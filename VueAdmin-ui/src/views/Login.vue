<template>
  <el-row type="flex" class="row-bg" justify="center">
    <el-col :xl="6" :lg="7">
      <div class="login-form"><h2>欢迎来到VueAdmin管理系统</h2>
        <el-image style="width: 180px; height: 180px" :src="require('@/assets/zwq.jpg')"></el-image>
        <p> 公众号 MarkerHub </p>
        <p> 扫码二维码，回复【VueAdmin】获取登录密码 </p></div>
    </el-col>
    <el-col :xl="6" :lg="7">
      <el-divider direction="vertical"></el-divider>
    </el-col>
    <el-col :xl="6" :lg="7">
      <el-form label-position="right" :rules="rules" label-width="80px" :model="loginForm" ref="loginForm">
        <el-form-item label="用户名" prop="username" style="width: 380px;">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" style="width: 380px;">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code" style="width: 380px;">
          <el-input v-model="loginForm.code" style="width: 172px; float: left;" maxlength="5"></el-input>
          <el-image class="captchaImg" :src="captchaImg" @click="getCaption"></el-image>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('loginForm')">提交</el-button>
          <el-button @click="getPass">获取密码</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<script>
import qs from 'qs'

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: 'admin ',
        password: '111111',
        code: '1111',
        token: ''
      },
      captchaImg: null,
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ], code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 4, max: 4, message: '长度在 4个字符', trigger: 'blur'}
        ],


      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        console.log('token:'+this.loginForm.token)



        if (valid) {
          this.$axios.post('/login?' + qs.stringify(this.loginForm)).then(res => {
            this.$store.commit('SET_TOKEN', res.data.data)
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 获取验证码
    getCaption() {
      this.$axios.get('/captcha').then(res => {
        console.log('res')
        console.log(res)
        this.loginForm.token = res.data.data.key
        this.captchaImg = res.data.data.captchaImg

        console.log('token:'+this.loginForm.token)
        console.log('code:'+this.loginForm.code)


      })

    }


  },
  created() {
    this.getCaption()
  }
}
</script>

<style scoped>
html, body {

  height: 100%;

}

.el-divider {
  height: 200px;

}

.captchaImg {
  float: left;
  margin-left: 8px;
  border-radius: 4px;
}

.el-row {
  background-color: #fafafa;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;

}
</style>

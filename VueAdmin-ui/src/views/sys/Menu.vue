<template>
  <div>

    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        border
        stripe
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
          prop="name"
          label="名称"
          sortable
          width="180">
      </el-table-column>
      <el-table-column
          prop="prem"
          label="权限编码"
          sortable
          width="180">
      </el-table-column>
      <el-table-column
          prop="icon"
          label="图标">
      </el-table-column>
      <el-table-column
          prop="type"
          label="类型">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type===0">目录</el-tag>
          <el-tag size="small" v-else-if="scope.row.type===1" type="success">菜单</el-tag>
          <el-tag size="small" v-else-if="scope.row.type===2" type="info">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="path"
          label="菜单URL">
      </el-table-column>
      <el-table-column
          prop="component"
          label="菜单组件">
      </el-table-column>
      <el-table-column
          prop="orderNum"
          label="排序">
      </el-table-column>

      <el-table-column
          prop="statu"
          label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.statu===1" type="success">正常</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu===2" type="danger">禁用</el-tag>

        </template>
      </el-table-column>
      <el-table-column
          prop="orderNum"
          label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row)">编辑</el-button>
          <el-divider direction="vertical"></el-divider>
          <template>
            <el-popconfirm title="这是一段内容确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>


        </template>

      </el-table-column>

    </el-table>
    <!-- 新增弹框 -->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">
      <el-form :model="editMenuForm" :rules="editFormRules" ref="editMenuForm" label-width="100px"
               class="demo-editMenuForm">

        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="editMenuForm.parentId" placeholder="请选择上级菜单">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>


              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ "- " + child.name }}</span>
                </el-option>

              </template>

            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="菜单名称" prop="name" label-width="100px">
          <el-input v-model="editMenuForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="perms" label-width="100px">
          <el-input v-model="editMenuForm.perms" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon" label-width="100px">
          <el-input v-model="editMenuForm.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" prop="path" label-width="100px">
          <el-input v-model="editMenuForm.path" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="菜单组件" prop="component" label-width="100px">
          <el-input v-model="editMenuForm.component" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="类型" prop="type" label-width="100px">
          <el-radio-group v-model="editMenuForm.type">
            <el-radio :label=0>目录</el-radio>
            <el-radio :label=1>菜单</el-radio>
            <el-radio :label=2>按钮</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="状态" prop="statu" label-width="100px">
          <el-radio-group v-model="editMenuForm.statu">
            <el-radio :label=1>禁用</el-radio>
            <el-radio :label=2>正常</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="排序号" prop="orderNum" label-width="100px">
          <el-input-number v-model="editMenuForm.orderNum" :min="1" label="排序号"></el-input-number>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('editMenuForm')">立即创建</el-button>
          <el-button @click="resetForm('editMenuForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Menu",
  data() {
    return {
      editMenuForm: {},
      editFormRules: {
        parentId: [
          {required: true, message: '请选择上级菜单', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
        perms: [
          {required: true, message: '请输入权限编码', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请选择类型', trigger: 'blur'}
        ],
        orderNum: [
          {required: true, message: '请填入排序号', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },
      dialogVisible: false,
      tableData: []
    }
  },
  methods: {
    getMenuTree() {
      this.$axios.get('/sys/menu/list').then(res => {
        this.tableData = res.data.data

      })
    },
    // 修改
    editHandle(row) {
      this.editMenuForm = row

      this.dialogVisible = true
      console.log('获取的值:'+row.orderNum)

    },
    // 清空
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editMenuForm = {}
    },
    handleClose() {
      this.resetForm('editMenuForm')
    },

    // 删除
    delHandle(id) {
      this.$axios.post("/sys/menu/delete/" + id).then(res => {
        this.$message({
          showClose: true,
          message: '恭喜你，操作成功',
          type: 'success',
          onClose:() => {
            this.getMenuTree()
          }
        });

      })

    },

    // 添加
    submitForm(editMenuForm) {

      this.$refs[editMenuForm].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/menu/' + (this.editMenuForm.id ? 'update' : 'save'), this.editMenuForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '恭喜你，操作成功',
                  type: 'success',
                  onClose: () => {
                    this.getMenuTree()
                  }
                });
                this.dialogVisible = false
              })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }

  },
  created() {
    this.getMenuTree()
  }
}
</script>

<style scoped>

</style>
<template>

  <div id="login_body">
    <div><Header></Header></div>
    <div id="login_card">
      <el-card style="border-radius: 15px">
        <div style="text-align: center;font-size: 25px;"><span>注册账号</span></div>
        <el-main>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="ruleForm.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="ruleForm.email"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="ruleForm.password"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button id="submit_button" type="primary" @click="submitForm('ruleForm')">注册</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-main>
      </el-card>

    </div>


  </div>

</template>

<script>
import {register} from "@/api/user"
import router from "@/router";
import Header from "@/components/Header";
import Element from "element-ui";

export default {
  name: "Register",
  components: {Header},
  data() {
    return {
      ruleForm: {
        username: '',
        password: '',
        email: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur'},
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['change'] }
        ]
      }
    };
  },

  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          register(this.ruleForm).then(res => {

            this.$router.push({path: '/login'})

          });
        } else {
          Element.Message.error("请输入正确的信息");
          return false
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 160px;
  margin-left: -30px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.demo-ruleForm{
  max-width: 500px;
  margin: 0 auto;
}

#login_body {
  width: 100%;
  height: 738px;

  background-color: #8EC5FC;
  background-image: linear-gradient(62deg, #8EC5FC 0%, #E0C3FC 50%, #5179cd 100%);
  background-position: top center;
}

#login_card {
  width: 500px;
  height: 300px;
  margin: 180px auto;
}

#submit_button {
  position: relative;
  left: -40px;
}


</style>
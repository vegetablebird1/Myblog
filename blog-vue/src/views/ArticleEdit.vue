<template>
  <div>
    <div><Header></Header></div>

    <div id="edit_body">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="文章类别" prop="categoryId">
          <el-select v-model="ruleForm.categoryId" placeholder="请输入文章类别">
            <el-option label="Java" value="1"></el-option>
            <el-option label="Linux" value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="摘要" prop="digest">
          <el-input type="textarea" v-model="ruleForm.digest"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
        </el-form-item>

        <el-form-item class="edit_button">
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          <el-button><router-link class="return_button" to="/articles">返回主界面</router-link></el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import {editArticle, getArticleDetail} from "@/api/article";
export default {
  name: "ArticleEdit",
  components: {Header},

  data() {
    return {
      ruleForm: {
        articleId: '',
        categoryId: '',
        title: '',
        digest: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 3, max: 30, message: '长度在 3 到 30 个字符', trigger: 'blur' }
        ],
        categoryId: [
          { required: true, message: '请选择文章标签', trigger: 'blur' }
        ],
        digest: [
          { required: true, message: '请输入摘要', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {

          const _this = this;
          editArticle(this.ruleForm,{
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            console.log(res)

            this.$alert('操作成功', '信息', {
              confirmButtonText: '确定',
              callback: action => {
                _this.$router.push("/articles")
              }
            });

          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    //取出edit中的articleId参数,如果为修改操作
    const articleId = this.$route.params.articleId
    console.log(articleId)
    const _this = this
    if (articleId) {
      getArticleDetail(articleId).then(res => {
        const article = res.data.data;
        _this.ruleForm.articleId = article.articleId
        _this.ruleForm.categoryId = article.categoryId
        _this.ruleForm.title = article.title
        _this.ruleForm.digest = article.digest
        _this.ruleForm.content = article.content
      })
    }
  }
}
</script>


<style scoped>

#edit_body {
  width: 1100px;
  margin: 60px auto;
}

.edit_button {
  position: relative;
  left: 350px;
}

.return_button {
  text-decoration: none;
}

</style>
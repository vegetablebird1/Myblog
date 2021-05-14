<template>
  <div>
    <div><Header></Header></div>

    <div id="detail_body">
      <el-card style="border-radius: 10px">

        <div slot="header" class="clearfix" style="text-align: center;">
          <el-button id="return_button"  type="text">
            <router-link class="link_button" :to="{path: '/articles'}">
              <el-button plain>返回</el-button>
            </router-link>
          </el-button>
          <span style="font-size: 20px;line-height: 50px">{{article.title}}</span>
          <el-button v-if="owner" id="del_button" type="danger" plain @click="delArticle(article.articleId)">删除</el-button>
<!--          <el-link v-if="owner" style="float: right; padding: 13px 10px" icon="el-icon-edit" >-->
<!--            <router-link class="link_button" :to="{name: 'ArticleEdit',params: {articleId: article.articleId}}">-->
<!--              编辑-->
<!--            </router-link>-->
<!--          </el-link>-->
          <router-link v-if="owner" :to="{name: 'ArticleEdit',params: {articleId: article.articleId}}">
            <el-button id="edit_button" type="primary" plain>编辑</el-button>
          </router-link>

        </div>


        <div class="markdown-body" v-html="article.content"></div>

      </el-card>
    </div>

  </div>
</template>

<script>
import Header from "@/components/Header";
import "github-markdown-css"
import {delArticleById, getArticleDetail} from "@/api/article";
export default {
  name: "ArticleDetail",
  components: {Header},
  data() {
    return {
      article: {
        articleId: '',
        userId: '',
        title: 'title',
        digest: 'digest',
        content: 'content',

      },
      owner: false
    }
  },

  methods: {
    delArticle(articleId){
      delArticleById(articleId).then(res => {
        this.$alert('删除成功', '信息', {
          confirmButtonText: '确定',
          callback: action => {
            this.$router.push("/articles")
          }
        });
      })
    }
  },

  created() {
    const articleId = this.$route.params.articleId;
    console.log(articleId);
    const _this = this;
    getArticleDetail(articleId).then(res => {
      const article = res.data.data;
      _this.article.articleId = article.articleId;
      _this.article.title = article.title;

      //渲染markdown编辑器的格式
      var MarkDownIt = require("markdown-it");
      var md = new MarkDownIt()

      var result = md.render(article.content);

      _this.article.digest = article.digest;
      _this.article.content = result;
      _this.owner = (article.userId === _this.$store.getters.getUser.userId)
    })
  }
}
</script>

<style scoped>

* {
  margin: 0 auto;
  padding: 0 0;
}

#detail_body {
  width: 1100px;
  margin: 60px auto;
}

/*消除塌陷*/
.clearfix:after {
  content: '';
  display: block;
  clear: both;
}

#return_button {
  padding: 5px 10px;
  float: left;
}


#del_button {
  float: right;
}


#edit_button{
  float: right;
  margin-right: 10px;
}


</style>
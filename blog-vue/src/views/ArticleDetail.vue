<template>
  <div class="articleDetail">
    <Header></Header>

    <div>
      <h1 style="text-align: center">{{article.title}}</h1>
      <el-link v-show="owner" icon="el-icon-edit">
        <router-link class="edit_button" :to="{name: 'ArticleEdit',params: {articleId: article.articleId}}">
          编辑
        </router-link>
      </el-link>
      <el-divider></el-divider>
      <h2>{{article.digest}}</h2>
      <el-divider></el-divider>


      <!--使用v-html渲染-->
      <div class="markdown-body" v-html="article.content"></div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import "github-markdown-css"
import {getArticleDetail} from "@/api/article";
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

.articleDetail {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 20px 15px;
}

.edit_button {
  text-decoration: none;
}


</style>
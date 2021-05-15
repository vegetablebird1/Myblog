<template>
  <div>

    <div><Header></Header></div>


    <div id="articles_body" v-cloak>
      <el-card class="box-card" v-for="article in articles" :key="article">
        <span id="a_title">
          <router-link class="a_detailLink" :to="{name: 'ArticleDetail',params: {articleId: article.articleId}}">
            {{article.title}}
          </router-link>
        </span>
        <el-divider></el-divider>
        <span><p>{{article.digest}}</p></span>
      </el-card>
    </div>

    <div>
      <el-pagination class="a_page"
                     :page-size="pageSize"
                     :page-count="10"
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :total="total"
                     @current-change="page"
      >
      </el-pagination>
    </div>

    <div v-cloak><Footer></Footer></div>


  </div>
</template>

<script>

import {getArticles} from "@/api/article";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
  name: "Articles",
  components: {Footer, Header},
  data() {
    return {
      articles: {},
      currentPage: 1,
      total: 0,
      pageSize: 5,
      createTime: ''
    }
  },

  methods: {
    page(currentPage){
      getArticles(currentPage).then(res => {
        this.articles = res.data.data.records
        this.currentPage = res.data.data.current
        this.total = res.data.data.total
        this.pageSize = res.data.data.size
      })
    }
  },
  created() {
    this.page(1)
  }
}

</script>

<style>

* {
  margin: 0 auto;
  padding: 0;
}

#articles_body {
  width: 1100px;
  margin: 60px auto ;
}

.box-card {
  margin: 25px;
  border-radius: 10px;
}

#a_title {
  font-size: 25px;
}

.a_detailLink {
  text-decoration: none;
}

.a_detailLink:hover {
  color: #14a09f;
}


.a_page {
  margin: 50px 0 auto;
  text-align: center;
}

[v-cloak] {
  display: none;
}
</style>
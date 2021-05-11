<template>
  <div>
    <div><Header></Header></div>

    <div>
      <el-timeline>
        <el-timeline-item :timestamp="article.createTime" placement="top" v-for="article in articles">
          <el-card>
            <h4>
              <router-link class="detail_button" :to="{name: 'ArticleDetail',params: {articleId: article.articleId}}">{{article.title}}</router-link>
            </h4>
            <p>{{article.digest}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>

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
    </div>
  </div>
</template>

<script>

import {getArticles} from "@/api/article";
import Header from "@/components/Header";
export default {
  name: "Articles",
  components: {Header},
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

<style scoped>

.a_page {
  margin: 0 auto;
  text-align: center;
}

.detail_button {
  text-decoration: none;
}

</style>
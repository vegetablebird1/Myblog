<template>
  <div>
    <Header></Header>

    <div id="timeLine_body">
      <el-timeline class="timeLine_content">
        <el-timeline-item :timestamp="article.createTime" placement="top" v-for="article in articles">
          <el-card style="border-radius: 10px">
            <p><router-link class="detail_link" :to="{name: 'ArticleDetail',params: {articleId: article.articleId}}">{{article.title}}</router-link></p>
          </el-card>
        </el-timeline-item>

      </el-timeline>
    </div>

    <div>
      <el-pagination class="page_divide"
                     :page-size="pageSize"
                     :page-count="10"
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :total="total"
                     @current-change="page"
      >
      </el-pagination>
    </div>

    <div><Footer></Footer></div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import {getArticles} from "@/api/article";
import Footer from "@/components/Footer";
export default {
  name: "TimeLine",
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

#timeLine_body {
  width: 1100px;
  margin: 60px auto;
}

.page_divide {
  text-align: center;

}

.detail_link {
  text-decoration: none;
}
</style>
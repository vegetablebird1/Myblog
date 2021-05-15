<template>
  <div>
    <div><Header></Header></div>

    <div id="aboutMe_body">
      <el-card class="box-card">
        <div class="text">
          <span>自我介绍:</span>

          <p>
            现在我是一名的大三学生，还在学习中。平时空闲时喜欢打打球，正在努力学习Java中。好好学习，天天向上。o(*￣︶￣*)o
          </p>
          <p>
            这是我第一次创建自己的网站博客，功能比较简陋，还有许多需要改进之处，大家如果看到不足之处，欢迎大家联系我，提出改进建议，相信我的网站会越来越好的。
          </p>
          <p>
            博客登陆，评论等模块待开发中...
          </p>
        </div>
      </el-card>
    </div>

    <div id="blog_foot"><Footer></Footer></div>

  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/Footer";
export default {
  name: "AboutMe",
  components: {Footer, Header}
}
</script>

<style scoped>

#aboutMe_body {
  width: 800px;
  margin: 60px auto;
}

.text {
  font-size: 20px;
}

p {
  text-indent: 2em;
}

.box-card {
  border-radius: 10px;
}

#blog_foot {
  position: relative;
  top: 400px;
}

</style>
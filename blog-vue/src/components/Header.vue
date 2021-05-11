<template>
  <div class="b_header">

    <h1>欢迎来到Ming的博客</h1>

    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
    </div>

    <div class="divider">

      <span><el-link type="warning" href="/articles">主页</el-link></span>
      <el-divider v-show="hasLogin" direction="vertical"></el-divider>
      <span v-show="hasLogin"><el-link type="success" href="/article/add">发表博客</el-link></span>
      <el-divider  direction="vertical"></el-divider>
      <span v-show="!hasLogin"><el-link type="primary" href="/login">登录</el-link></span>

      <span v-show="hasLogin"><el-link type="danger" @click="userLogout">退出</el-link></span>
    </div>
  </div>
</template>

<script>
import request from "@/request";
import {logout} from "@/api/login";

export default {
  name: "Header",
  data() {
    return {
      user:{
        username: '',
        avatar: 'https://himg.bdimg.com/sys/portraitn/item/68b37a6a6d717131353897be',

      },
      hasLogin: false
    }
  },
  methods: {
    userLogout() {
      const _this = this;
      // request.get("/logout",{
      //   headers: {
      //     "Authorization": localStorage.getItem("token")
      //   }
      // }).then(res => {
      //   _this.$store.commit("REMOVE_INFO");
      //   _this.$router.push("/login");
      // })

      logout().then(res => {
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push('/login')
      })

    }
  },

  created() {
    console.log("++++++++++++")
    if (this.$store.getters.getUser.username){
      this.user.username = this.$store.getters.getUser.username
      this.user.avatar = this.$store.getters.getUser.avatar
      this.hasLogin = true
      console.log("************")
    }
    console.log('++++++++++++')
  }
}
</script>

<style scoped>

.b_header{
  max-width: 960px;
  margin: 0 auto;
  text-align: center;
}

.divider{
  margin: 50px;
}
</style>
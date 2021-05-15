<template>

  <!-- 导航栏 -->
  <div>
    <div class="nav">
      <el-menu class="el-menu-button" mode="horizontal">
        <el-menu-item index="-1"><a href="/articles"><el-avatar :src="user.avatar"></el-avatar></a></el-menu-item>
        <el-menu-item index="0"><a href="/articles">欢迎来到Ming的博客</a></el-menu-item>
        <el-menu-item v-if="isLogin" index="6"><router-link :to="{path: '/article/add'}">发表博客</router-link></el-menu-item>
        <el-menu-item class="nav_button" index="1"><a href="/articles">主页</a></el-menu-item>
        <el-menu-item class="nav_button" index="2"><a href="/timeLine">时间线</a></el-menu-item>
        <el-menu-item v-if="!isLogin" class="nav_button" index="3"><a href="/login" >登陆</a></el-menu-item>
        <el-menu-item v-if="isLogin" class="nav_button" index="4" @click="userLogout"><a>退出登陆</a></el-menu-item>
        <el-menu-item class="nav_button" index="5"><a href="/aboutMe" >关于我</a></el-menu-item>
      </el-menu>
    </div>

  </div>

</template>

<script>
import {logout} from "@/api/login";
import router from "@/router";

export default {
  name: "Header",
  data(){
    return {
      isLogin: false,
      user: {
        username: 'Ming',
        avatar: 'https://himg.bdimg.com/sys/portraitn/item/68b37a6a6d717131353897be'
      },
      searchKey: '',
    }
  },

  methods:{
    userLogout() {
      logout().then(res => {
        this.$store.commit('REMOVE_INFO')
        router.push('/login')
      })
    }
  },

  created() {
    if (this.$store.getters.getUser.username){
      this.isLogin = true;
    }
  }
}
</script>


<style>

* {
  margin: 0;
  padding: 0;
}

a {
  text-decoration: none;
}

a:hover {
  color: #B3C0D1;
}

.nav_button{
  position: relative;
  left: 900px;
}


</style>
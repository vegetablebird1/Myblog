import Vue from 'vue'
import VueRouter from 'vue-router'
import Articles from "@/views/Articles";
import Login from "@/views/Login";
import ArticleEdit from "@/views/ArticleEdit";
import ArticleDetail from "@/views/ArticleDetail";
import TimeLine from "@/views/TimeLine";
import AboutMe from "@/views/AboutMe";
import Register from "@/views/Register";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        redirect : {name: "Articles"}
    },
    {
        path: '/articles',
        name: 'Articles',
        component: Articles
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/article/add',
        name: 'ArticleAdd',
        component: ArticleEdit,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/article/:articleId',
        name: 'ArticleDetail',
        component: ArticleDetail
    },
    {
        path: '/article/:articleId/edit',
        name: 'ArticleEdit',
        component: ArticleEdit,
        meta: {
            requireAuth: true
        }
    },
    {
        path: '/timeLine',
        name: 'TimeLine',
        component: TimeLine
    },
    {
        path: '/aboutMe',
        name: 'AboutMe',
        component: AboutMe
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

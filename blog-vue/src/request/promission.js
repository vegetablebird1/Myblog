import router from "@/router";

//路由判断登录

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requireAuth)){
        //判断路由是否需要登录权限
        const token = localStorage.getItem("token");

        console.log("---------" + token);

        //有token
        if (token) {
            next()
        } else {
            if (to.name === '/login'){
                next()
                return
            }
            next({
                path: "/login"
            })
        }
    }else {
        next();
    }
})
//封装axios
//实现axios前置拦截和后置拦截请求

import axios from 'axios'
import Element from 'element-ui'
import router from '../router'
import store from '../store'

const request = axios.create({
    // baseURL: "http://localhost:8000",

    baseURL: "http://112.74.51.45:12306",

    //超时时间5s
    timeout: 5000
})

//前置拦截
request.interceptors.request.use(config => {

    return config

})

//后置拦截，出现异常错误，出现弹窗

request.interceptors.response.use(response => {
        let res = response.data;

        console.log("====================");
        console.log(res);
        console.log("====================");

        if (res.code === 200){
            //正常
            return response;
        }else {
            Element.Message.error(response.data.msg)
            //阻止进入then里面,返回异常信息
            return Promise.reject(response.data.msg);
        }
    },
    //报错 Assert.notNull
    error => {
        console.log(error);

        if (error.response.data){
            error.message = error.response.data.msg;
        }

        //未认证
        if (error.response.status === 401){
            store.commit("REMOVE_INFO");
            router.push("/login");
        }

        Element.Message.error(error.message);
        return Promise.reject(error);
    })

export default request
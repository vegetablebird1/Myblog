import request from '@/request'

//获得所有文章
export function getArticles(currentPage) {
    //返回的是promise对象，直接返回
    return request({
        url: '/articles',
        method: 'get',
        //params会拼接成currentPage=**
        params: {
            currentPage: currentPage
        }
    })
}

//获得文章详情
export function getArticleDetail(articleId) {
    return request({
        url: `/article/${articleId}`,
        method: 'get'
    })
}

//编辑文章
export function editArticle(article) {
    return request({
            url: '/article/edit',
            method: 'post',
            data: {
                articleId: article.articleId,
                categoryId: article.categoryId,
                userId: article.userId,
                title: article.title,
                digest: article.digest,
                content: article.content
            },
            headers: {
                'Authorization': localStorage.getItem("token")
            }
        })
}

//删除文章
export function delArticleById(articleId){
    return request({
        url: `/article/delete/${articleId}`,
        method: 'get',
        headers: {
            'Authorization': localStorage.getItem("token")
        }
    })
}



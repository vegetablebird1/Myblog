import request from "@/request";

export function getComments(articleId) {
    return request({
        url: `/comment/${articleId}`,
        method: 'get'
    })
}

export function addComment(comment) {
    return request({
        url: '/comment/add',
        method: 'post',
        data: comment,
        headers: {
            'Authorization': localStorage.getItem("token")
        }
    })
}
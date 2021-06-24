import request from "@/request";

export function getViews() {
    return request({
        url: '/history/views',
        method: 'get'
    })
}
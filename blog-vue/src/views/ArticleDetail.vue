<template>
  <div>
    <div><Header></Header></div>

    <div id="detail_body">
      <el-card style="border-radius: 10px">

        <div slot="header" class="clearfix" style="text-align: center;">
          <el-button id="return_button"  type="text">
            <router-link class="link_button" :to="{path: '/articles'}">
              <el-button plain>返回</el-button>
            </router-link>
          </el-button>
          <span style="font-size: 20px;line-height: 50px">{{article.title}}</span>
          <el-button v-if="owner" id="del_button" type="danger" plain @click="delArticle(article.articleId)">删除</el-button>
          <router-link v-if="owner" :to="{name: 'ArticleEdit',params: {articleId: article.articleId}}">
            <el-button id="edit_button" type="primary" plain>编辑</el-button>
          </router-link>

        </div>


        <div class="markdown-body" v-html="html"> </div>

      </el-card>
    </div>

    <!-- 评论   -->
    <div class="reply-box">

      <!-- 回复栏 -->
      <div v-if="userInfo.userId" v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
        <el-avatar  class="avatar-img" :size="40" :src="userInfo.avatar"></el-avatar>
        <div class="reply-info" >
          <div
              tabindex="0"
              contenteditable="true"
              id="replyInput"
              spellcheck="false"
              placeholder="输入评论..."
              class="reply-input"
              @focus="showReplyBtn"
              @input="onDivInput($event)"
          >
          </div>
        </div>

        <div class="reply-btn-box" v-show="btnShow">
          <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表评论</el-button>
        </div>

      </div>

      <!-- 详细评论 -->
      <div v-for="(comment,index) in article.comments" :key="index">
        <el-avatar class="avatar-img" :size="40" :src="comment.avatar"></el-avatar>
        <div class="author-info">
          <span class="author-name">{{comment.username}}</span>
          &nbsp;&nbsp;&nbsp;   &nbsp;&nbsp;&nbsp;     &nbsp;&nbsp;&nbsp;
          <span class="author-time">{{comment.createTime}}</span>
        </div>
        <p v-html="comment.content"></p>

        <el-divider></el-divider>
      </div>

    </div>


  </div>
</template>

<script>
import Header from "@/components/Header";
import "github-markdown-css"
import {delArticleById, getArticleDetail} from "@/api/article";
import {addComment, getComments} from "@/api/comment";

const clickoutside = {
// 初始化指令
  bind(el, binding, vnode) {
    function documentHandler(e) {
// 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
// 判断指令中是否绑定了函数
      if (binding.expression) {
// 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }
// 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.vueClickOutside = documentHandler;
    document.addEventListener('click', documentHandler);
  },
  update() {},
  unbind(el, binding) {
// 解除事件监听
    document.removeEventListener('click', el.vueClickOutside);
    delete el.vueClickOutside;
  },
};

export default {
  name: "ArticleDetail",
  components: {Header},
  data() {
    return {
      article: {
        articleId: '',
        userId: '',
        title: 'title',
        digest: 'digest',
        content: 'content',
        comments: []
      },

      comment: {
        commentId: '',
        articleId: '',
        parentId: '',
        userId: '',
        username: '',
        avatar: '',
        content: '',
        createTime: '',
        children: []
      },

      html: '',
      owner: false,

      // 用户信息
      userInfo: {
        avatar: 'https://himg.bdimg.com/sys/portraitn/item/68b37a6a6d717131353897be',
        email: '',
        userId: '',
        username: ''
      },
      btnShow: false
    }
  },

  directives: {clickoutside},

  methods: {
    delArticle(articleId){
      delArticleById(articleId).then(res => {
        this.$alert('删除成功', '信息', {
          confirmButtonText: '确定',
          callback: action => {
            this.$router.push("/articles")
          }
        });
      })
    },

    //刷新评论
    refreshComments() {
      getComments(this.article.articleId).then(res => {
        this.article.comments = res.data.data
      })
    },

    //发送评论
    sendComment() {
      addComment(this.comment).then(res => {
        this.refreshComments();
        this.$notify({
          title: '成功',
          message: '添加评论成功',
          type: 'success'
        })
      })
    },

    //TODO 输入方法
    inputFocus(){
      var replyInput = document.getElementById('replyInput');
      replyInput.style.padding= "8px 8px"
      replyInput.style.border ="2px solid blue"
      replyInput.focus()
    },
    hideReplyBtn(){
      this.btnShow = false
      replyInput.style.padding= "10px"
      replyInput.style.border ="none"
    },
    showReplyBtn() {
        this.btnShow = true
    },
    onDivInput: function(e) {
      this.comment.articleId = this.article.articleId
      this.comment.userId = this.userInfo.userId
      this.comment.content = e.target.innerHTML;
    },

  },

  created() {
    const articleId = this.$route.params.articleId

    this.userInfo = this.$store.getters.getUser

    getArticleDetail(articleId).then(res => {
      const article = res.data.data
      this.article.articleId = article.articleId
      this.article.title = article.title

      this.article.comments = article.comments

      //渲染markdown编辑器的格式
      const MarkDownIt = require("markdown-it")
      const md = new MarkDownIt();

      const result = md.render(article.content)

      this.article.digest = article.digest
      this.article.content = article.content
      this.html = result
      this.owner = (article.userId === this.$store.getters.getUser.userId)
    })
  }
}
</script>

<style>

* {
  margin: 0 auto;
  padding: 0 0;
}

#detail_body {
  width: 1100px;
  margin: 60px auto;
}

/*消除塌陷*/
.clearfix:after {
  content: '';
  display: block;
  clear: both;
}

#return_button {
  padding: 5px 10px;
  float: left;
}


#del_button {
  float: right;
}


#edit_button{
  float: right;
  margin-right: 10px;
}

/*回复栏*/
.reply-box {
  width: 1100px;
  padding: 10px;
  background-color: #fafbfc;
}

.avatar-img {
  display: inline-block;
  vertical-align: top;
}

.reply-info {
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}

.author-info {
  display: inline-block;
  margin-left: 5px;
  width: 60%;
  height: 40px;
  line-height: 20px;
}

.author-name {
  color: #000;
  font-size: 18px;
  font-weight: bold;
}

.author-time {
  font-size: 14px;
}
</style>
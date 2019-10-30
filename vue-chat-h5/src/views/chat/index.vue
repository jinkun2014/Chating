<template>
    <div>
        <section class="top">
            <img class="head" src="../../assets/ico-exit.png" @click="exit"/>
            <span class="name">{{'群聊('+userCount+')'}}</span>
            <div class="voice" @click="changeVoice">
                <img v-if="voiceOpen" class="icon" src="../../assets/soundOn.svg"/>
                <img v-else class="icon" src="../../assets/soundClose.svg"/>
            </div>
        </section>

        <section ref="chatList" class="chat-list">
            <ul>
                <template v-for="item in records">
                    <li class="chat-mine" v-if="item.type==1">
                        <div class="chat-user"><img src="../../assets/user.png"></div>
                        <div class="time"><cite><i>{{item.time}}</i>{{item.name}}</cite></div>
                        <div class="chat-text">{{item.content}}</div>
                    </li>
                    <li v-if="item.type==2">
                        <div class="chat-user"><img src="../../assets/default.png"></div>
                        <div class="time"><cite>{{item.name}}<i>{{item.time}}</i></cite></div>
                        <div class="chat-text">{{item.content}}</div>
                    </li>
                </template>
            </ul>
        </section>

        <section class="foot">
            <van-cell-group>
                <van-field
                        ref="inputContent"
                        v-model="content"
                        center
                        clearable
                        autosize
                        rows="1"
                        type="textarea"
                        placeholder="请输入聊天内容">
                    <van-button slot="button" size="small" type="primary" @click="sendMsg">发送</van-button>
                </van-field>
            </van-cell-group>
        </section>
    </div>
</template>

<script>
    import util from '@/utils'
    import {getToken, removeToken} from '@/utils/auth'

    import SockJS from 'sockjs-client';
    import Stomp from 'stompjs';


    export default {
        name: 'chatlist',
        data() {
            return {
                stompClient: null,
                voiceOpen: true,
                content: '',
                userCount: 0,
                //聊天记录
                records: [
                    // {
                    //     type: 1,
                    //     time: util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
                    //     name: '我',
                    //     content: '哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈' +
                    //         '哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈' +
                    //         '哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈哈哈哈哈哈啊哈！'
                    // },
                    // {
                    //     type: 2,
                    //     time: util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
                    //     name: '其它人',
                    //     content: '这里是<a target="_blank" href="https://github.com/taylorchen709/vue-chat">源码</a>'
                    // }
                ],
            }
        },
        methods: {
            //发送消息
            sendMsg() {
                let self = this;
                if (self.content == '') {
                    self.$toast('请输入消息!');
                    return;
                }
                // self.records.push(
                //     {
                //         type: 1,
                //         time: util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
                //         name: '我',
                //         content: this.content
                //     }
                // );
                self.stompClient.send("/from:app/messaging", {}, JSON.stringify({
                    'from': getToken(),
                    'content': self.content
                }));
                self.content = '';
                //滚动到底部
                self.scrollToBottom();
                //聚焦输入框
                self.focusTxtContent();
                // setTimeout(function () {
                //     self.records.push({
                //         type: 2,
                //         time: util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
                //         name: '其它人',
                //         content: '你好！'
                //     });
                //     //滚动到底部
                //     self.scrollToBottom();
                // }, 1000);
            },
            //聚焦输入框
            focusTxtContent() {
                this.$refs.inputContent.focus();
            },
            //滚动条滚动到底部
            scrollToBottom() {
                let self = this;
                setTimeout(function () {
                    let chatlist = self.$refs.chatList;
                    chatlist.scrollTop = chatlist.scrollHeight;
                }, 100);
            },
            changeVoice() {
                this.voiceOpen = !this.voiceOpen
            },
            showMessage(messge) {
                let self = this;
                self.records.push({
                    type: messge.from == getToken() ? 1 : 2,
                    time: util.formatDate.format(new Date(messge.time), 'yyyy-MM-dd hh:mm:ss'),
                    name: messge.from == getToken() ? '自己' : '其它人',
                    content: messge.content
                });
                //滚动到底部
                self.scrollToBottom();
            },
            showGreeting(content) {
                let self = this;
                self.records.push({
                    type: 2,
                    time: util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
                    name: '系统提示',
                    content: content
                });
                //滚动到底部
                self.scrollToBottom();
            },
            login(loginId) {
                this.stompClient.send("/from:app/login", {}, JSON.stringify({"action": "login", "loginId": loginId}));
            },
            logout() {
                console.log("logout")
                this.stompClient.disconnect();
                // 退出登录
                // removeToken();

                this.$dialog.alert(
                    {
                        message: '登录失效,请重新登录!'
                    }
                ).then(() => {
                    // on close
                    this.$router.replace({path: '/login'})
                });
            },
            exit() {

                this.$dialog.confirm({
                    title: '提示',
                    message: '确认退出当前群聊,是否继续?'
                }).then(() => {
                    // 断开连接
                    this.stompClient.disconnect();

                    // 清空Token
                    removeToken();

                    // 跳转登录页
                    this.$router.replace({path: '/login'})
                }).catch(() => {
                    // on cancel
                });
            }
        },
        // updated: function () {
        //     this.scrollToBottom();
        //     this.focusTxtContent();
        // }
        created() {
            let self = this;
            // 初始化连接
            let socket = new SockJS(process.env.VUE_APP_BASE_API + '/chatroom');
            self.stompClient = Stomp.over(socket);
            let headers = {
                Authorization: "Token"   // 获取token
            }
            self.stompClient.connect(headers, (frame) => {   // 连接
                let loginId = new Date().getTime();
                console.log(frame);
                // 登录&登出
                self.stompClient.subscribe("/from:server/greetings", function (response) {
                    console.log(`1:${response.body}`)
                    let greeting = JSON.parse(response.body);
                    self.userCount = greeting.userCount;
                    self.showGreeting(greeting.content);
                });

                // 群聊
                self.stompClient.subscribe("/from:server/messaging", function (response) {
                    console.log(`2:${response.body}`)
                    self.showMessage(JSON.parse(response.body));
                })

                // 单聊-主要是主动断开连接
                self.stompClient.subscribe("/from:server/single/" + getToken() + loginId, function (response) {
                    console.log(`3:${response.body}`)
                    // self.showMessage(JSON.parse(response.body).content);
                    self.logout();
                })

                // 单点登录
                self.login(loginId);
            }, (err) => {
                console.log(err)
                // 清空Token
                removeToken();

                this.$dialog.alert(
                    {
                        message: '登录失效,请重新登录!'
                    }
                ).then(() => {
                    // on close
                    this.$router.replace({path: '/login'})
                });
            })
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    .chat-list {
        position: absolute;
        top: 60px;
        bottom: 48px;
        left: 0px;
        right: 0px;
        overflow-y: scroll;
        overflow-x: hidden;
        padding: 10px;
    }

    .chat-list ul {
        min-height: 300px;
    }

    .chat-list ul .chat-mine {
        text-align: right;
        padding-left: 0;
        padding-right: 60px;
    }

    .chat-list ul li {
        position: relative;
        /*font-size: 0;*/
        margin-bottom: 10px;
        padding-left: 60px;
        min-height: 68px;
    }

    .chat-mine .chat-user {
        left: auto;
        right: 3px;
    }

    .chat-user {
        position: absolute;
        left: 3px;
    }

    .chat-text,
    .chat-user {
        display: inline-block;
        vertical-align: top;
        /*font-size: 14px;*/
    }

    .chat-user img {
        width: 40px;
        height: 40px;
        border-radius: 100%;
    }

    .time {
        width: 100%;
    }

    cite {
        left: auto;
        right: 60px;
        text-align: right;
    }

    cite {
        font-style: normal;
        line-height: 24px;
        font-size: 12px;
        white-space: nowrap;
        color: #999;
        text-align: left;
    }

    cite i {
        font-style: normal;
        padding-left: 5px;
        padding-right: 5px;
        font-size: 12px;
    }

    .chat-mine .chat-text {
        margin-left: 0;
        text-align: left;
        background-color: #33DF83;
        color: #fff;
    }

    .chat-text {
        position: relative;
        line-height: 22px;
        /*margin-top: 25px;*/
        padding: 10px 15px;
        background-color: #eee;
        border-radius: 3px;
        color: #333;
        word-break: break-all;
        max-width: 462px \9;
    }

    .chat-text,
    .chat-user {
        display: inline-block;
        vertical-align: top;
        font-size: 14px;
    }

    .chat-text img {
        max-width: 100%;
        vertical-align: middle;
    }

    .chat-user {
        position: absolute;
        left: 3px;
    }

    .chat-text:after {
        content: '';
        position: absolute;
        left: -10px;
        top: 15px;
        width: 0;
        height: 0;
        border-style: solid dashed dashed;
        border-color: #eee transparent transparent;
        overflow: hidden;
        border-width: 10px;
    }

    .chat-mine .chat-text:after {
        left: auto;
        right: -10px;
        border-top-color: #33DF83;
    }

    .top {
        height: 50px;
        line-height: 50px;
        position: absolute;
        top: 0px;
        left: 0px;
        right: 0px;
        background-color: #F8F8F8;
        padding: 5px 10px;
    }

    .top .head {
        width: 50px;
        height: 50px;
        float: left;
        border-radius: 100%;
    }

    .top .name {
        font-size: 18px;
        color: #444;
        margin-left: 10px;
    }

    .top .voice {
        width: 30px;
        height: 50px;
        float: right;
        text-align: center;
    }

    .top .voice .icon {
        color: #000;
        height: 20px;
        width: 30px;
        margin-top: 15px;
    }

    .foot {
        width: 100%;
        min-height: 52px;
        position: fixed;
        bottom: 0px;
        left: 0px;
        padding: 1px;
        background-color: #F8F8F8;
    }
</style>

<template>
    <div>
        <div style="text-align: center;">
            <img src="../../assets/logo.png" style="width:250px;height: 250px;">
        </div>
        <van-cell-group>
            <van-field
                    v-model="phone"
                    label="手机号"
                    label-align="left"
                    placeholder="请输入手机号"
                    :error-message="phoneErr"/>
            <van-field
                    v-model="code"
                    center
                    clearable
                    label="验证码"
                    label-align="left"
                    placeholder="请输入短信验证码"
                    :error-message="codeErr">
                <van-button color="#000" slot="button" size="small" :disabled="time!=60" @click="getCode">{{time==60?'发送验证码':time+' s'}}</van-button>
            </van-field>
        </van-cell-group>
        <div style="text-align: center;">
            <van-button color="#d39e5a" style="width: 92%;margin-top: 20px" @click="login">登录</van-button>
        </div>
    </div>
</template>

<script>
    import schema from 'async-validator'
    import {setToken} from '@/utils/auth'
    import * as login from '@/api/login'

    export default {
        data() {
            return {
                phone: '13163261065',
                phoneErr: '',
                code: '123456',
                codeErr: '',
                time: 60,
                rules: {
                    phone: [
                        {required: true, message: "手机号码不允许为空!"},
                        {pattern: /^(1[0-9][0-9])\d{8}$/, message: "手机号码格式不正确!"}
                    ],
                    code: [
                        {required: true, message: "验证码不允许为空!"},
                        {pattern: /^\d{6}$/, message: "验证码格式不正确!"}
                    ],
                }
            }
        },
        methods: {
            resetErr() {
                this.phoneErr = ''
                this.codeErr = ''
            },
            handleErrors(errors, fields) {
                for (let i in errors) {
                    if (errors[i].field === 'phone') {
                        this.phoneErr = errors[i].message
                    }
                    if (errors[i].field === 'code') {
                        this.codeErr = errors[i].message
                    }
                }
            },
            getCode() {
                this.time--
                let clock = window.setInterval(() => {
                    this.time--
                    if (this.time < 0) {
                        window.clearInterval(clock)
                        this.time = 60
                    }
                }, 1000)
            },
            login() {
                const params = {phone: this.phone, code: this.code};
                const validator = new schema(this.rules);
                validator.validate(params, async (errors, fields) => {
                    this.resetErr();
                    if (errors) {
                        console.info(errors)
                        return this.handleErrors(errors, fields);
                    }
                    // // 登录
                    // const response = await login.login(params)
                    // const {token} = response.data;
                    // if (token && token != '') {
                    //     setToken(token)
                    //     this.$router.replace({path: '/'})
                    // } else {
                    //     this.$toast.fail('登录失败!');
                    // }
                    setToken("test")
                    this.$router.replace({path: '/'})
                }).catch(() => console.log('promise catch err'));
            }
        }
    }
</script>
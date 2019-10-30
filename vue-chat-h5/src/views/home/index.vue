<template>
    <div>
        <van-grid :column-num="3">
            <van-grid-item
                    v-for="item in items"
                    :key="item.key"
                    :icon="item.icon"
                    :text="item.text"
                    :to="item.to"/>
        </van-grid>
    </div>
</template>

<script>
    import {removeToken} from '@/utils/auth'
    import * as login from '@/api/login'

    export default {
        data() {
            return {
                title: '',
                items: [
                    {
                        key: 'vip',
                        icon: 'friends',
                        text: 'VIP订单',
                        to: '/vipOrder/create'
                    }
                ]
            }
        },
        methods: {
            async onClickLogout() {
                await login.logout()
                removeToken()
                this.$router.replace({path: '/login'})
            }
        },
        created() {
            this.$router.replace({path: '/chat'})
        }
    }
</script>

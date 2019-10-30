<template>
    <div>
        <van-cell-group>
            <van-field
                    v-model="vipOrderForm.phone"
                    required
                    clearable
                    size="large"
                    label="用户手机"
                    placeholder="请输入用户手机号码"
                    required
                    :error-message="phoneErr"
            />
            <van-field
                    v-model="vipOrderForm.amount"
                    label="金额"
                    clearable
                    size="large"
                    placeholder="请输入密码"
                    required
                    :error-message="amountErr"
            />
            <van-field
                    readonly
                    clickable
                    size="large"
                    label="客服电话"
                    :value="vipOrderForm.servicePhone"
                    placeholder="选择客服电话"
                    @click="showServiceChoice = true"
                    required
                    :error-message="servicePhoneErr"
            />
        </van-cell-group>

        <van-popup v-model="showServiceChoice" position="bottom">
            <van-picker
                    show-toolbar
                    :columns="serviceList"
                    value-key="phone"
                    @cancel="showServiceChoice = false"
                    @confirm="handleServiceChoose"
            />
        </van-popup>
        <div style="padding: 32px 16px;">
            <van-button round type="danger" style="width: 100%" @click="onSubmit">{{isEdit?'更新':'创建'}}</van-button>
            <van-button round type="default" style="width: 100%;margin: 10px 0 0 0" @click="onCancel">取消</van-button>
        </div>
    </div>
</template>

<script>
    import schema from 'async-validator'
    import * as vipOrder from '@/api/vipOrder'
    import * as servicePhone from '@/api/servicePhone'

    const defaultForm = {
        id: '',
        phone: '',
        amount: 1000,
        servicePhone: ''
    }

    export default {
        name: 'Detail',
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                vipOrderForm: {
                    id: '',
                    phone: '',
                    amount: 1000,
                    servicePhone: ''
                },
                phoneErr: '',
                amountErr: '',
                servicePhoneErr: '',
                rules: {
                    phone: [
                        {required: true, message: "请输入手机号码"},
                        // {pattern: /^(1[0-9][0-9])\d{8}$/, message: "手机格式不正确"}
                        {pattern: /^\d+$/, message: "手机格式不正确"}
                    ],
                    amount: [
                        {required: true, message: "请输入金额"},
                        {pattern: /^\d+$/, message: '金额必须为数字值'}
                    ],
                    servicePhone: [
                        {required: true, message: "请选择正确的客服号码"},
                        // {pattern: /^(1[0-9][0-9])\d{8}$/, message: "手机格式不正确"}
                        {pattern: /^\d+$/, message: "手机格式不正确"}
                    ],
                },
                serviceList: [
                    // {
                    //   id: "1",
                    //   phone: "13800001111"
                    // }
                ],
                showServiceChoice: false,
            }
        },
        methods: {
            handleServiceChoose(value) {
                this.vipOrderForm.servicePhone = value.phone;
                this.showServiceChoice = false;
            },
            async fetchServicePhoneList() {
                try {
                    let response = await servicePhone.list({pageNo: 1, pageSize: 1000, status: 1})
                    let {list, totalCount} = response.data
                    this.serviceList = list
                } catch (e) {
                    console.error(e)
                }
            },
            resetErr() {
                this.phoneErr = ''
                this.amountErr = ''
                this.servicePhoneErr = ''
            },
            handleErrors(errors, fields) {
                for (let i in errors) {
                    if (errors[i].field === 'phone') {
                        this.phoneErr = errors[i].message
                    }
                    if (errors[i].field === 'amount') {
                        this.amountErr = errors[i].message
                    }
                    if (errors[i].field === 'servicePhone') {
                        this.servicePhoneErr = errors[i].message
                    }
                }
            },
            onSubmit() {
                const validator = new schema(this.rules);
                validator.validate(this.vipOrderForm, async (errors, fields) => {
                    this.resetErr();
                    if (errors) {
                        console.info(errors)
                        return this.handleErrors(errors, fields);
                    }

                    this.$dialog.confirm({
                        title: '提示',
                        message: `订单创建成功后将直接打款[${this.vipOrderForm.amount}]到用户手机号[${this.vipOrderForm.phone}],无法修改和撤回,请确认无误后再操作, 是否继续?`
                    }).then(async () => {
                        await vipOrder.add(this.vipOrderForm);
                        this.$notify({type: 'success', message: '充值成功!'});
                        Object.assign(this.vipOrderForm, defaultForm)
                    }).catch(() => {

                    });
                }).catch(() => console.log('promise catch err'));
            },
            onCancel() {
                // this.$dialog.alert({
                //     title: '标题',
                //     message: '弹窗内容'
                // }).then(() => {
                //     // on close
                // });
            },
            async fetchData(id) {
                this.form = Object.assign({}, defaultForm);
            },
        },
        async created() {
            this.fetchServicePhoneList();

            if (this.isEdit) {
                const id = this.$route.params && this.$route.params.id
                await this.fetchData(id)
            } else {
                this.form = Object.assign({}, defaultForm)
            }
        }
    }
</script>

<style>
</style>


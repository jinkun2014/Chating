(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-aafc6ae0"],{"02f4":function(e,r,t){var n=t("4588"),a=t("be13");e.exports=function(e){return function(r,t){var o,i,c=String(a(r)),s=n(t),u=c.length;return s<0||s>=u?e?"":void 0:(o=c.charCodeAt(s),o<55296||o>56319||s+1===u||(i=c.charCodeAt(s+1))<56320||i>57343?e?c.charAt(s):o:e?c.slice(s,s+2):i-56320+(o-55296<<10)+65536)}}},"0390":function(e,r,t){"use strict";var n=t("02f4")(!0);e.exports=function(e,r,t){return r+(t?n(e,r).length:1)}},"0bfb":function(e,r,t){"use strict";var n=t("cb7c");e.exports=function(){var e=n(this),r="";return e.global&&(r+="g"),e.ignoreCase&&(r+="i"),e.multiline&&(r+="m"),e.unicode&&(r+="u"),e.sticky&&(r+="y"),r}},"214f":function(e,r,t){"use strict";t("b0c5");var n=t("2aba"),a=t("32e9"),o=t("79e5"),i=t("be13"),c=t("2b4c"),s=t("520a"),u=c("species"),l=!o(function(){var e=/./;return e.exec=function(){var e=[];return e.groups={a:"7"},e},"7"!=="".replace(e,"$<a>")}),d=function(){var e=/(?:)/,r=e.exec;e.exec=function(){return r.apply(this,arguments)};var t="ab".split(e);return 2===t.length&&"a"===t[0]&&"b"===t[1]}();e.exports=function(e,r,t){var f=c(e),p=!o(function(){var r={};return r[f]=function(){return 7},7!=""[e](r)}),g=p?!o(function(){var r=!1,t=/a/;return t.exec=function(){return r=!0,null},"split"===e&&(t.constructor={},t.constructor[u]=function(){return t}),t[f](""),!r}):void 0;if(!p||!g||"replace"===e&&!l||"split"===e&&!d){var h=/./[f],v=t(i,f,""[e],function(e,r,t,n,a){return r.exec===s?p&&!a?{done:!0,value:h.call(r,t,n)}:{done:!0,value:e.call(t,r,n)}:{done:!1}}),m=v[0],b=v[1];n(String.prototype,e,m),a(RegExp.prototype,f,2==r?function(e,r){return b.call(e,this,r)}:function(e){return b.call(e,this)})}}},"520a":function(e,r,t){"use strict";var n=t("0bfb"),a=RegExp.prototype.exec,o=String.prototype.replace,i=a,c="lastIndex",s=function(){var e=/a/,r=/b*/g;return a.call(e,"a"),a.call(r,"a"),0!==e[c]||0!==r[c]}(),u=void 0!==/()??/.exec("")[1],l=s||u;l&&(i=function(e){var r,t,i,l,d=this;return u&&(t=new RegExp("^"+d.source+"$(?!\\s)",n.call(d))),s&&(r=d[c]),i=a.call(d,e),s&&i&&(d[c]=d.global?i.index+i[0].length:r),u&&i&&i.length>1&&o.call(i[0],t,function(){for(l=1;l<arguments.length-2;l++)void 0===arguments[l]&&(i[l]=void 0)}),i}),e.exports=i},"5f1b":function(e,r,t){"use strict";var n=t("23c6"),a=RegExp.prototype.exec;e.exports=function(e,r){var t=e.exec;if("function"===typeof t){var o=t.call(e,r);if("object"!==typeof o)throw new TypeError("RegExp exec method returned something other than an Object or null");return o}if("RegExp"!==n(e))throw new TypeError("RegExp#exec called on incompatible receiver");return a.call(e,r)}},"7ded":function(e,r,t){"use strict";t.d(r,"b",function(){return o}),t.d(r,"c",function(){return i}),t.d(r,"a",function(){return c});t("96cf");var n=t("3b8d"),a=t("b775"),o=function(){var e=Object(n["a"])(regeneratorRuntime.mark(function e(r){return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].post("/login",r);case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}},e)}));return function(r){return e.apply(this,arguments)}}(),i=function(){var e=Object(n["a"])(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].get("/logout");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}(),c=function(){var e=Object(n["a"])(regeneratorRuntime.mark(function e(){return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].get("/code");case 2:return e.abrupt("return",e.sent);case 3:case"end":return e.stop()}},e)}));return function(){return e.apply(this,arguments)}}()},a481:function(e,r,t){"use strict";var n=t("cb7c"),a=t("4bf8"),o=t("9def"),i=t("4588"),c=t("0390"),s=t("5f1b"),u=Math.max,l=Math.min,d=Math.floor,f=/\$([$&`']|\d\d?|<[^>]*>)/g,p=/\$([$&`']|\d\d?)/g,g=function(e){return void 0===e?e:String(e)};t("214f")("replace",2,function(e,r,t,h){return[function(n,a){var o=e(this),i=void 0==n?void 0:n[r];return void 0!==i?i.call(n,o,a):t.call(String(o),n,a)},function(e,r){var a=h(t,e,this,r);if(a.done)return a.value;var d=n(e),f=String(this),p="function"===typeof r;p||(r=String(r));var m=d.global;if(m){var b=d.unicode;d.lastIndex=0}var x=[];while(1){var w=s(d,f);if(null===w)break;if(x.push(w),!m)break;var E=String(w[0]);""===E&&(d.lastIndex=c(f,o(d.lastIndex),b))}for(var y="",k=0,F=0;F<x.length;F++){w=x[F];for(var R=String(w[0]),S=u(l(i(w.index),f.length),0),$=[],j=1;j<w.length;j++)$.push(g(w[j]));var C=w.groups;if(p){var O=[R].concat($,S,f);void 0!==C&&O.push(C);var _=String(r.apply(void 0,O))}else _=v(R,f,S,$,C,r);S>=k&&(y+=f.slice(k,S)+_,k=S+R.length)}return y+f.slice(k)}];function v(e,r,n,o,i,c){var s=n+e.length,u=o.length,l=p;return void 0!==i&&(i=a(i),l=f),t.call(c,l,function(t,a){var c;switch(a.charAt(0)){case"$":return"$";case"&":return e;case"`":return r.slice(0,n);case"'":return r.slice(s);case"<":c=i[a.slice(1,-1)];break;default:var l=+a;if(0===l)return t;if(l>u){var f=d(l/10);return 0===f?t:f<=u?void 0===o[f-1]?a.charAt(1):o[f-1]+a.charAt(1):t}c=o[l-1]}return void 0===c?"":c})}})},b0c5:function(e,r,t){"use strict";var n=t("520a");t("5ca1")({target:"RegExp",proto:!0,forced:n!==/./.exec},{exec:n})},d1a2:function(e,r,t){"use strict";t.r(r);var n=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",[e._m(0),t("van-cell-group",[t("van-field",{attrs:{size:"large",clearable:"",label:"登录名","label-align":"left",placeholder:"请输入登录名","error-message":e.loginFormErr.usernameErr},model:{value:e.loginForm.username,callback:function(r){e.$set(e.loginForm,"username",r)},expression:"loginForm.username"}}),t("van-field",{attrs:{size:"large",clearable:"",label:"密码","label-align":"left",placeholder:"请输入密码",type:"password","error-message":e.loginFormErr.passwordErr},model:{value:e.loginForm.password,callback:function(r){e.$set(e.loginForm,"password",r)},expression:"loginForm.password"}}),e.showCode()?t("van-field",{attrs:{size:"large",center:"",clearable:"",label:"验证码","label-align":"left",placeholder:"请输入验证码","error-message":e.loginFormErr.codeErr},model:{value:e.loginForm.code,callback:function(r){e.$set(e.loginForm,"code",r)},expression:"loginForm.code"}},[t("div",{staticStyle:{"text-align":"center",width:"100px",height:"30px"},attrs:{slot:"right-icon"},slot:"right-icon"},[t("van-image",{attrs:{width:"100%",height:"100%",fit:"fill",src:e.codePic},on:{click:e.changeCodePic}})],1)]):e._e()],1),t("div",{staticStyle:{"text-align":"center"}},[t("van-button",{staticStyle:{width:"92%","margin-top":"20px"},attrs:{color:"#d39e5a"},on:{click:e.login}},[e._v("登录")]),t("van-button",{staticStyle:{width:"92%","margin-top":"20px"},attrs:{color:"#d39e5a"},on:{click:e.loginSimple}},[e._v("一键登录")])],1)],1)},a=[function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticStyle:{"text-align":"center",height:"250px","line-height":"250px"}},[t("span",{staticStyle:{"font-size":"30px",color:"black"}},[e._v("Go Go Go")])])}],o=(t("a481"),t("96cf"),t("3b8d")),i=t("2a95"),c=t("5f87"),s=t("7ded"),u={usernameErr:"",passwordErr:"",codeErr:""},l={data:function(){return{loginForm:{username:"",password:"",code:"",codeKey:""},loginFormErr:{usernameErr:"",passwordErr:"",codeErr:""},codePic:"",rules:{username:[{required:!0,message:"请输入登录名!"}],password:[{required:!0,message:"请输入密码!"}],code:[{required:this.showCode(),message:"请输入验证码!"}]}}},methods:{showCode:function(){return!1},changeCodePic:function(){this.loadCode()},resetErr:function(){Object.assign(this.loginFormErr,u)},handleErrors:function(e){for(var r in e)"username"===e[r].field&&(this.loginFormErr.usernameErr=e[r].message),"password"===e[r].field&&(this.loginFormErr.passwordErr=e[r].message),"code"===e[r].field&&(this.loginFormErr.codeErr=e[r].message)},login:function(){var e=this,r=new i["a"](this.rules);r.validate(this.loginForm,function(){var r=Object(o["a"])(regeneratorRuntime.mark(function r(t,n){var a,o;return regeneratorRuntime.wrap(function(r){while(1)switch(r.prev=r.next){case 0:if(e.resetErr(),!t){r.next=3;break}return r.abrupt("return",e.handleErrors(t));case 3:return r.next=5,s["b"](e.loginForm);case 5:a=r.sent,o=a.data.token,o&&""!=o?(Object(c["c"])(o),e.$router.replace({path:"/"})):e.$toast.fail("登录失败!");case 8:case"end":return r.stop()}},r)}));return function(e,t){return r.apply(this,arguments)}}()).catch(function(){return console.log("promise catch err")})},loginSimple:function(){var e=Object(o["a"])(regeneratorRuntime.mark(function e(){var r,t;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return this.loginForm.username=(new Date).getTime()+this.randomNum(0,100),this.loginForm.password=this.randomNum(0,100),e.next=4,s["b"](this.loginForm);case 4:r=e.sent,t=r.data.token,t&&""!=t?(Object(c["c"])(t),this.$router.replace({path:"/"})):this.$toast.fail("登录失败!");case 7:case"end":return e.stop()}},e,this)}));function r(){return e.apply(this,arguments)}return r}(),randomNum:function(e,r){switch(arguments.length){case 1:return parseInt(Math.random()*e+1,10);case 2:return parseInt(Math.random()*(r-e+1)+e,10);default:return 0}},loadCode:function(){var e=Object(o["a"])(regeneratorRuntime.mark(function e(){var r,t,n,a;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,s["a"]();case 2:r=e.sent,t=r.data,n=t.key,a=t.url,this.codePic=a,this.loginForm.codeKey=n;case 6:case"end":return e.stop()}},e,this)}));function r(){return e.apply(this,arguments)}return r}()},created:function(){this.showCode()&&this.loadCode()}},d=l,f=t("2877"),p=Object(f["a"])(d,n,a,!1,null,null,null);r["default"]=p.exports}}]);
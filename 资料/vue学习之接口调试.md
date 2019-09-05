#                                VUE学习之接口调试

[TOC]

## 一、项目创建

1、全局安装vue的脚手架

> npm[^cnpm] install -g vue-cli 

2、通过webpack初始化一个项目,项目名称为my-project

> vue init webpack my-project 

3、进入到刚刚初始化的项目中

> cd my-project

4、安装项目依赖

> npm install

5、运行项目

> npm run dev

如果你想构建项目，使用如下命令即可

> npm run build

[^cnpm]: 使用npm比较慢，可以使用淘宝镜像 npm install -g cnpm --registry=https://registry.npm.taobao.org, 之后的npm可以使用cnpm进行替换。

## 二、使用axios发送请求

首先项目中引入axios依赖

> npm install axios

依赖安装好之后需要在使用的的地方进行中引入在项目中新建了一个axios.js

```javascript
import axios from 'axios'
const instance = axios.create({
  // basic url
  baseURL: 'http://ucarabs.10101111.com/ucarlpp',
  // 超时时间
  timeout: 3000,
  withCredentials: true
})
export default instance
```

这样就可以创建一个axios实例了，只有在需要使用axios的js中引入新建的axios.js即可，这样就可以直接使用导出的axios实例了。

接下来新建一个api.js，引入axios，我们要发送一个POST请求：

```javascript
// 这个是我们后面新建的axios.js
import axios from '@/axios'
function exportExcel (param) {
  return axios({
    method: 'POST',
    url: '/excel/doExport.do',
    params: param
  })
}
export default {exportExcel}
```

method可以是POST,GET,PUT,DELETE等请求，里面还有许多的请求参数，请求的需要传入的数据params，headers等等。在需要此方法的vue组件中使用该方法，引入导出的方法就可以。

```javascript
import {exportExcel} from '@/api'
export default {
    //省略其他不相关代码
    data() {
        return {
             param: ''
        }
    }
    methods: {
        myExportExcel() {
    exportExcel(this.param).then((data) => {
    	// do success logic, handle the data
    	// data from server
    })
        .catch((error) => {
            // do error logic
        })
        }
    }
}
```

其他的请求也多类似，具体的看业务，具体情况具体分析。

## 三、调试小插曲

在调试的时候发现调试失败，感觉请求的url也没有什么问题

> // 这是请求的url
>
> http://ucarabs.10101111.com:9528/ucarlpp/excel/importData.do_?serviceName=ucarAisStockImport 

![error](C:\Users\guofei.wu\AppData\Local\Temp\1530078319322.png)

![request error](C:\Users\guofei.wu\AppData\Local\Temp\1530078438493.png)

在看一下别的接口请求正常的url

![normal](C:\Users\guofei.wu\AppData\Local\Temp\1530078553119.png)

对比一下Request URL发现多了端口号9528，问题出在在访问getUserInfo这个接口是通过axios发送的GET请求，而importData.do_是通过组件的submit方法提交的，所以其完整的url

> ucarabs.10101111.com:9528/ucarlpp/importData.do_?serviceName=AisStockImport

而我们服务端的端口是使用的80，端口号对不上所以出现404错误，如果端口号和服务器端的端口号一致肯定可以访问。

解决方案是配置代理，在项目的config/index.js中配置代码

```javascript
proxyTable: {
      '/ucarlpp': {
        target: 'http://ucarabs.10101111.com',
        changeOrigin: true,
        pathRewrite: {
          '^/ucarlpp/': '/ucarlpp/'
        }
      }
    }
```

将以/ucarlpp开头的全以/ucarlpp/覆盖target就可以了。修改了配置文件需要重新跑一下。

![right](C:\Users\guofei.wu\AppData\Local\Temp\1530081033957.png)

ok~，请求成功，ok，关于vue学习之接口调试到此结束，若需要学习了解更多关于**[vue](https://cn.vuejs.org/v2/guide/index.html)**和**[axios](https://www.npmjs.com/package/axios)**可以去官网学习。:happy:

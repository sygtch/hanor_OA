(function ($, window, document, layer, undefined) {
    if (undefined == $) {
        throw new Error("该插件需要jquery支持");
    }
    if (undefined == layer) {
        console.log("该插件建议使用layer");
        layer = {
            load: function () {

            },
            close: function () {

            },
            msg: function (msg) {
                alert(msg);
            }
        }
    }
    $.extend({
        //异步文件上传
        upload: function (conf) {
            var data = new FormData();
            for (var k in conf.data) {
                data.append(k, conf.data[k]);
            }
            $.send($.extend(conf, {
                type: 'POST',
                data: data,
                processData: false,  // 告诉jQuery不要去处理发送的数据
                contentType: false  // 告诉jQuery不要去设置Content-Type请求头
            }));
        },
        //判断是否为空
        isBlank: function (obj) {
            return "undefined" == typeof obj || undefined == obj || null == obj || "" == obj;
        },
        //发送请求，同ajax
        send: function (config) {
            var index = layer.load(1);
            $.ajax($.extend({
                //提交数据的类型 POST GET
                type: "GET",
                //提交的网址
                url: null,
                //提交的数据
                data: null,
                //返回数据的格式
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                //在请求之前调用的函数
                beforeSend: function () {
                },
                //成功返回之后调用的函数
                success: function (result) {
                },
                //调用执行后调用的函数
                complete: function (XMLHttpRequest, textStatus) {
                    layer.close(index);
                },
                //调用出错执行的函数
                error: function (XMLHttpRequest, textStatus) {
                    //请求出错处理
                    console.log(XMLHttpRequest);
                    var html = "<div class='orange'><i class=\"fa fa-warning\"></i>访问失败：" + XMLHttpRequest.status + "</div>";
                    layer.msg(html);
                }
            }, config));
        },
        //设置下拉列表选中值
        selected: function (selector, target) {
            selector.find("option").each(function () {
                if ($(this).val() == target) {
                    $(this).prop("selected", "selected");
                }
            });
        },
        //页面出错时
        error: function (fuc) {
            window.onerror = function (errorMessage, scriptURI, lineNumber, columnNumber, errorObj) {
                var msg = "url: " + window.location.href + "\n";
                msg += "errorMessage: " + errorMessage + "\n";
                msg += "scriptURI: " + scriptURI + "\n";
                msg += "lineNumber: " + lineNumber + "\n";
                msg += "columnNumber: " + columnNumber + "\n";
                msg += "errorObj: " + JSON.stringify(errorObj);
                fuc(msg, errorMessage, scriptURI, lineNumber, columnNumber, errorObj);
            }
        }
    });
})(jQuery, window, document, layer);
/*对Date的扩展，将 Date 转化为指定格式的String
月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
例子：
(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 */
Date.prototype.format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
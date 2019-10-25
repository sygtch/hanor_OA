(function ($, window, document, layer, undefined) {
    $.extend({
        tableDisplay: function (table_box, dis_box) {
            var _this = this;
            var h = "";
            table_box.find("th").each(function () {
                h += "<li><a href='#'>";
                h += "<input type='checkbox' checked='checked'>";
                h += $(this).text();
                h += "</a></li>";
            });
            dis_box.append(h);
            dis_box.find("a").click(function () {
                var _checkbox = $(this).find("input[type=checkbox]");
                _checkbox.prop("checked", !_checkbox.prop("checked"));
                _checkbox.change();
            });
            dis_box.find("input[type=checkbox]").change(function () {
                if ($(this).prop("checked")) {
                    _this.listShow($(this).parents("li").index());
                } else {
                    _this.listDis($(this).parents("li").index());
                }
            });
            this.listDis = function (col_num) {
                table_box.find("tr").each(function () {
                    if (0 == $(this).find("th").length) {
                        $(this).find("td").eq(col_num).hide();
                    } else {
                        $(this).find("th").eq(col_num).hide();
                    }
                });
            };
            this.listShow = function (col_num) {
                table_box.find("tr").each(function () {
                    if (0 == $(this).find("th").length) {
                        $(this).find("td").eq(col_num).show();
                    } else {
                        $(this).find("th").eq(col_num).show();
                    }
                });
            };
        },
        sendGet: function (jqObj) {
            var url = jqObj.attr("url");
            var before = jqObj.attr("before");
            var error = jqObj.attr("error");
            var success = jqObj.attr("success");
            var complete = jqObj.attr("complete");
            var index;
            $.send({
                url: url,
                //在请求之前调用的函数
                beforeSend: function (XMLHttpRequest) {
                    if ($.isBlank(before)) {
                        index = layer.load(1);
                    } else {
                        var bef = eval(before);
                        var res = bef(XMLHttpRequest);
                        if ("boolean" == (typeof res))
                            return res;
                    }
                },
                //成功返回之后调用的函数
                success: function (result) {
                    if ($.isBlank(success)) {
                        layer.msg(result.msg, {
                            time: 800,
                            end: function () {
                                if (1 == result.code) {
                                    location.reload();
                                }
                            }
                        });
                    } else {
                        var suc = eval(success);
                        suc(result);
                    }
                },
                //调用执行后调用的函数
                complete: function (XMLHttpRequest, textStatus) {
                    if (!$.isBlank(index)) {
                        layer.close(index);
                    }
                    if (!$.isBlank(complete)) {
                        var com = eval(complete);
                        com(XMLHttpRequest, textStatus);
                    }
                },
                //调用出错执行的函数
                error: function (XMLHttpRequest, textStatus) {
                    //请求出错处理
                    console.log(XMLHttpRequest);
                    if ($.isBlank(error)) {
                        var html = "<div class='orange'><i class=\"fa fa-warning\"></i>访问失败：" + XMLHttpRequest.status + "</div>";
                        layer.msg(html);
                    } else {
                        var err = eval(error);
                        err(XMLHttpRequest, textStatus);
                    }
                }
            });
        },
        autoHeight: function (config) {
            config = $.extend({
                selector: null,
                height: 0
            }, config);
            this.resize = function (conf) {
                var height = $(window).height();
                height -= $("#navbar").height();
                height -= $("#breadcrumbs").height() + 2;
                height -= $("#chart-form").height() + 8;
                height -= 75;
                height -= 32;
                if (-1 == String(conf.height).indexOf("%")) {
                    height += conf.height;
                } else {
                    height = height / 100 * Number(String(config.height).replace("%", ""));
                }
                conf.selector.height(height);
            };
            var _this = this;
            _this.resize(config);
            $(window).resize(function () {
                _this.resize(config);
            });
        }
    });

    (function (selector) {
        if ($.isBlank(selector))
            return;
        selector.each(function () {
            $(this).click(function () {
                $.sendGet($(this));
            });
        });
    })($(".send-get"));

    (function (table, box) {
        if ($.isBlank(table) || $.isBlank(box))
            return;
        $.tableDisplay(table, box);
    })($("#simple-table"), $(".list-cole-dis"));
    (function () {
        if (window != window.top) {
            $("#navbar").remove();
            $("#sidebar").remove();
            $("#breadcrumbs").remove();
        }
    })();
})(jQuery, window, document, layer);
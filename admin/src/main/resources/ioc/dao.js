var ioc = {
    dataSource: {
        type: "com.alibaba.druid.pool.DruidDataSource",
        events: {
            create: "init",
            depose: 'close'
        },
        fields: {
            url: {
                java: "$conf.get('db.url')"
            },
            username: {
                java: "$conf.get('db.username')"
            },
            password: {
                java: "$conf.get('db.password')"
            },
            testWhileIdle: true,
            validationQuery: {
                java: "$conf.get('db.validationQuery')"
            },
            maxActive: {
                java: "$conf.get('db.maxActive')"
            },
            filters: "mergeStat",
            connectionProperties: "druid.stat.slowSqlMillis=2000",
            maxWait: 5000 // 毫秒数,在5秒内拿不到新连接,就抛异常,而不是继续瞎等
        }
    },
    dao: {
        type: "org.nutz.dao.impl.NutDao",
        args: [{
            refer: "dataSource"
        }],
        fields: {
            executor: {refer: "cacheExecutor"}
        }
    },
    cacheExecutor: {
        type: "org.nutz.plugins.cache.dao.CachedNutDaoExecutor",
        fields: {
            cacheProvider: {refer: "cacheProvider"},
            // 需要缓存的表名
            cachedTableNames: ["sys_menu"]
        }
    },
    // 基于Ehcache的DaoCacheProvider
    cacheProvider: {
        type: "org.nutz.plugins.cache.dao.impl.provider.EhcacheDaoCacheProvider",
        fields: {
            cacheManager: {refer: "cacheManager"} // 引用ehcache.js中定义的CacheManager
        },
        events: {
            create: "init"
        }
    },
    checkPermissionFilter:{
        type:"org.nutz.mvc.filter.CheckPermission",
        args:[{refer:"dao"},"/login"]
    }
};
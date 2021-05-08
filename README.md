# Custom Prefix
## 介绍
一个简单的聊天插件，可以设置并储存玩家的前缀并保存<br>
每个玩家可以设置任意数量的前缀，每一个前缀都有一个键(slot)和一个值(prefix)<br>
特点就是玩家的前缀不是直接放到聊天上，而是作为papi变量被聊天管理插件读取<br>
这样非常方便，因为你可以统一管理多个前缀，并在聊天管理插件中统一管理<br>
有趣的是，这个东西不仅能储存玩家的前缀，还能储存任何关于玩家的信息（比如手机号码）<br>

## 命令
`/customprefix reload` : 重载配置文件

`/prefix info <target> <slot>` : 查看目标的slot槽位中的内容<br>
`/prefix set <target> <slot> <prefix>` : 把目标slot槽位上的值设为prefix<br>
`/prefix reset <target> <slot>` : 清除目标slot槽位上的值<br>
`/prefix list <target>` : 列出目标的所有前缀<br>

## 权限
`customprefix.customprefix` : 使用/customprefix命令<br>
`customprefix.prefix` : 使用/prefix命令

## PlaceholderAPI
其他插件可以通过PlaceholderAPI变量来读取玩家前缀<br>
`%customprefix_slot%` 会返回玩家slot槽位中的变量<br>
比如我在槽位test中的前缀是[TEST]，`%customprefix_test%`返回的就是[TEST]<br>

## Config.yml
几乎没什么东西，就是一些消息的自定义<br>

## Data.yml
储存着玩家的前缀<br>
格式为<br>
```yaml
玩家 :
  '槽位' : '格式'
  '槽位' : '格式'
玩家 :
  '槽位' : '格式'
  '槽位' : '格式'
```
## 其他
- 刚学这系列东西，可能有些地方代码规范不好或者不够优化，欢迎各种建议
- github新人，这里有啥规矩还不太懂，只是想把源代码公开来，供他人参考修改，有什么不妥的地方请谅解qwq
- 因为功能就这么点，以后应该很少会更新

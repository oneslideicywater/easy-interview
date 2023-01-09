## getopts

你所用的脚本，包括如`ls -al,ps -ef,ps --forest`这些风格的输入参数，脚本都是怎么识别这些参数的呢？

如果你开发一个类似的脚本，可以使用到`getopts`.

下面的函数模仿ls,实现一个简单的脚本。
```shell

# :代表有参数的选项
while getopts :ab:cd opt
do
	case "$opt" in
		a) echo "Found the -a option" ;;
		# $OPTARG代表当前选项的处理的值
		b) echo "Found the -b option, with value $OPTARG";;
		c) echo "Found the -c option";;
		d) echo "Found the -d option";;
		*) echo "Unknown option: $opt";;
	esac
done
# $OPTIND=1一开始，到这里等于5，代表下一个要处理的变量
shift $[ $OPTIND - 1 ]
count=1
for param in "$@"
do
	echo "Parameter $count: $param"
	count=$[ $count + 1 ]
done
```

调用脚本可以像ls等其他的shell命令那样：

```bash
bash parameter.sh -a -b test1 -d test2 test3 test4
```
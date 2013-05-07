编译器界面图:
===========================
NewSWTApp.java UI部分（SWT组件）
![github](http://img.blog.csdn.net/20130507155623487 "github")

运行方法  Usage 
---------------------------
CFanalyze.java 词法分析部分
---------------------------
在文本域输入你的代码（按照我给的文法  文法在myG.txt里）
![github](http://img.blog.csdn.net/20130507160016683 "github")
先点击第一个button生成词法分析
![github](http://img.blog.csdn.net/20130507160114071 "github")
YFanalyze.java 词法分析部分
---------------------------
再点击第二个button把代码转义成适配文法标识符的输入串
![github](http://img.blog.csdn.net/20130507160150805 "github")
点第三个Button进行语法分析   如果语法正确，显示accepted  如图：
![github](http://img.blog.csdn.net/20130507160228757 "github")
 如果语法错误，显示错误以及错误的地方
 YYanalyze.java 词法分析部分
---------------------------
点第四个button进行语义分析  生成四元式
![github](http://img.blog.csdn.net/20130507160254084 "github")
HBgenerate.java 词法分析部分
---------------------------
点第五个Button进行四元式翻译，翻译成汇编代码
![github](http://img.blog.csdn.net/20130507160315255 "github")

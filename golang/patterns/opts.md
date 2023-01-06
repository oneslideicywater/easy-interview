## Opts Pattern

通常情况下，创建一个新的对象会使用默认构造器。

> 假设现在我们要准备一个家庭医药包，里面有抵抗新冠的一些常用药，比如莲花清瘟，999感冒灵颗粒，藿香正气片 etc.

```go
package main

import "fmt"

// MedicalKit contains medicine that helps alleviate symptoms of COVID-19 virus
type MedicalKit struct {
	// 莲花清瘟
	LianHuaQingWenCapsule int

	// 999
	GanMaoLingKeLi int

	// 藿香正气片
	LophanthusAntiFebrileTablet int
}

// NewMedicalKit create a new default medical kit
func NewMedicalKit() *MedicalKit{
	return &MedicalKit{
		LianHuaQingWenCapsule: 1,
		GanMaoLingKeLi: 1,
		LophanthusAntiFebrileTablet: 1,
	}
}

func main() {
	kit:=NewMedicalKit()

	fmt.Println(kit)
}

```

那现在如果要创建的同时，修改对象的状态，怎么办？

你会很容易想到带参构造器:

```go
// NewMedicalKit create a new default medical kit
func NewMedicalKit(LianHuaQingWenCapsule int,LophanthusAntiFebrileTablet int,GanMaoLingKeLi int,) *MedicalKit{

	return &MedicalKit{
		LianHuaQingWenCapsule: LianHuaQingWenCapsule,
		GanMaoLingKeLi: GanMaoLingKeLi,
		LophanthusAntiFebrileTablet: LophanthusAntiFebrileTablet,
	}
}
```

但是这样有一个问题，如果我只想传入其中一个参数，保持其他为默认值，在调用的时候，我不得不将参数全部传入。

```go
kit:=NewMedicalKit(1,2,3)
```
另外一个问题是我需要记住参数传入的顺序，否则就会有差错。

那有没有一种方法，让我可以实现不需要关心参数传入顺序且不用传入非必要参数呢？答案是Opts模式

## Opts模式

Opts模式相比默认构造器有如下优势

- 封装内部对象如何更改状态自身
- 只需要向构造器传入想修改的参数，默认的不需要传
- 不需要去关心向构造器传入的参数的顺序
- 新增一种药需要加构造器参数，而Opts模式完全无需任何改动, 扩展性极好。


```go
package main

import "fmt"

// MedicalKit contains medicine that helps alleviate symptoms of COVID-19 virus
type MedicalKit struct {
	// 莲花清瘟
	LianHuaQingWenCapsule int

	// 999
	GanMaoLingKeLi int

	// 藿香正气片
	LophanthusAntiFebrileTablet int
}


var (
	DefaultLianHuaQingWenCapsuleCount =1
	DefaultGanMaoLingKeLiCount=1
	DefaultLophanthusAntiFebrileTabletCount=1
)
// MedicalKitOptions 可以更改构造器行为
type MedicalKitOptions func(*MedicalKit)

func NewMedicalKit(opts  ...MedicalKitOptions) *MedicalKit{
	kit:=&MedicalKit{
		LophanthusAntiFebrileTablet: DefaultLophanthusAntiFebrileTabletCount,
		GanMaoLingKeLi: DefaultGanMaoLingKeLiCount,
		LianHuaQingWenCapsule: DefaultLianHuaQingWenCapsuleCount,
	}
    // 执行每个option函数
	for _, opt :=range opts{
		opt(kit)
	}
	return kit
}


func main() {
	with999:= func(count int) MedicalKitOptions{
		return func(k *MedicalKit){
			k.GanMaoLingKeLi=count
		}
	}
	withLianHua:= func(count int) MedicalKitOptions{
		return func(k *MedicalKit) {
			k.LianHuaQingWenCapsule=count
		}
	}

	// without need to pass all arguments
	kit:=NewMedicalKit(with999(2),withLianHua(3))
	fmt.Println(kit)

}

```


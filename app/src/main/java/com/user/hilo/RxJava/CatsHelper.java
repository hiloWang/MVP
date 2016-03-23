package com.user.hilo.RxJava;

import android.net.Uri;

import com.user.hilo.RxJava.entity.Cat;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/3/23.
 * 唉，这样清晰简单的代码帅到让我窒息啊。来理清一下代码的炫酷之处吧。
 * 主方法 saveTheCutestCat 只包含了 3 个其它方法，然后花个几分钟来看看代码和思考这些方法。
 * 你给方法提供了输入参数然后就能得到结果返回了，在这个方法工作的时候我们需要等待它的完成。

     简洁而有用，让我们再看看组合方法的其它优势：
     组合
     正如我们看到的，根据其它 3 个方法而新创建了一个方法（saveTheCutestCat），因此我们组合了它们。
     像乐高积木那样，我们把方法之间连接起来组成了乐高积木（实际上可以在之后组合起来）。组合方法是很简单的，从一个方法得到返回结果然后再把它传递给另外的方法做为输入参数，这不简单吗？
     错误的传递
     另外一个好处就是我们处理错误的方式了。任何一个方法都可能因执行时发生错误而被终止，
     这个错误能在任何层次上被处理掉，Java 中我们叫它抛出了异常，然后这个错误在 try/catch 代码块中做处理。
     这里的关键点是我们不需要为组合方法里的每个方法都做异常处理，仅需要对这些组合起来的方法做统一处理，像下面这样：
 */
public class CatsHelper {

    Api api;

    public Uri saveTheCutestCat(String query) {
        try {
            List<Cat> cats = api.queryCats(query);
            Cat cat = findCutest(cats);
            Uri saveUri = api.store(cat);
            return saveUri;
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.parse("");
        }
    }

    private Cat findCutest(List<Cat> cats) {
        return Collections.max(cats);
    }
}

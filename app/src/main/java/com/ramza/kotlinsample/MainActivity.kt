package com.ramza.kotlinsample

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ramza.kotlinsample.dialog.showNotesAlertDialog
import com.ramza.kotlinsample.github.Result
import com.ramza.kotlinsample.github.SearchRepositoryProvider
import com.ramza.kotlinsample.util.await
import com.ramza.kotlinsample.util.bindSharedPreference
import com.ramza.kotlinsample.wiki.WikiApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.nield.kotlinstatistics.countBy
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val wikiApiServe by lazy {
        WikiApiService.create()
    }
    var disposable: Disposable? = null

    lateinit var testTextView : TextView
    lateinit var testButton : Button

    private var preferenceValue : Int by bindSharedPreference(this, "PerferenceKey", 0)

    private var notesDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UI(true) {
            verticalLayout {
                padding = dip(8)
                testTextView = textView{
                    text = "Test"
                }
                testButton = button {
                    onClick {
                        startActivity<SecondActivity>("name" to "test")
                    }
                    text = "Toast"
                }.lparams(width = wrapContent)
                button {
                    text = "Browse"
                    onClick {
                        browse("https://makery.co")
                    }
                }
                button {
                    text = "Share"
                    onClick {
                        share("share", "subject")
                    }
                }
                button {
                    text = "Alert"
                    onClick {
                        alert(Appcompat, "Kotlin", "Kotlin is so fresh!") {
                            customView { editText{
                                hint = "test"
                            } }
                            positiveButton("OK") {
                                snackbar(testTextView, "This is a snack!")
                            }
                            negativeButton("Cancel") {
                                /*load {
                                    searchNotSuspend()
                                } then {
                                    testTextView.text = "There are ${it.items.size} Java developers in Korea"
                                }*/
                                /*launch(Background){
                                    val result = search()
                                    launch(UI){
                                        testTextView.text = "There are ${result.items.size} Java developers in Korea"
                                    }
                                }*/
                                /*async(UI){
                                    val result = search()
                                    result?.let {
                                        testTextView.text = "There are ${it.items.size} Java developers in Korea"
                                    }
                                }*/
                                /*doAsync {
                                    val result = SearchRepositoryProvider.provideSearchRepository2().searchUser2("Korea", "Java").execute().body()
                                    uiThread {
                                        result?.let {
                                            testTextView.text = "There are ${it.items.size} Java developers in Korea"
                                        }
                                    }
                                }*/

                                /*val future = doAsyncResult {
                                    SearchRepositoryProvider.provideSearchRepository2().searchUser2("Korea", "Java").execute().body()
                                }
                                doAsync {
                                    val result = future.get()
                                    uiThread {
                                        result?.let {
                                            testTextView.text = "There are ${it.items.size} Java developers in Korea"
                                        }
                                    }
                                }*/
                                launch{
                                    try{
                                        val request = wikiApiServe.hitCountCheck2("query", "json", "search", "test")
                                        val response = request.await()
                                        if(response.isSuccessful){
                                            val result = response.body()!!;
                                            testTextView.text = "${result.query.searchinfo.totalhits} result found"
                                        }else{

                                        }
                                    }catch (exception : Exception){

                                    }
                                }
                            }
                        }.show()
                    }
                }
            }
        }

        asyncAwait()

        greetAndHello()

        var composedVar = composed(val1 = 1)
        testTextView.text = composedVar.toString()

        val numbers =  arrayListOf<Int>(-42, 17, 13, -9, 12)
        val nonNegative = numbers.filter { it >= 0 }
        println(nonNegative)

        listOf(1, 2, 3, 4) // list of 1, 2, 3, 4
                .map { it * 10 } // maps to to 10, 20, 30, 40
                .filter { it > 20 } // filters out 30, 40
                .forEach { print(it) } // prints 30, 40

        var test = KotlinPerson()
        try{
            test.age = -45
        }catch (e : java.lang.IllegalArgumentException){
            println(e.message)
        }
        testTextView.text = test.age?.toString()?:"test"

//        beginSearch("test")

        val list = listOf(1,2,3,4,5,6,7,8,9)
        val res = filter(list, listOf(),{it>5} )
        testTextView.text = res.toString()

        testTextView.text = Person(name = "t").name

//        val test2 = fail()

        var persons = listOf(Person("test3", "test"), Person("test2", "test2"))
        persons.sortedBy { it.name }
        testTextView.text = persons.sortedBy { it.name }.countBy{it.name}.toString()

        val repository = SearchRepositoryProvider.provideSearchRepository()
        repository.searchUsers("Korea", "Java")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    result ->
                    testTextView.text = "There are ${result.items.size} Java developers in Korea"
                }, { error ->
                    error.printStackTrace()
                })

        println(listOf(2,2,2) - 2) // [2, 2]
        println(listOf(2,2,2) - listOf(2)) // empty list

        testTextView.setOnClickListener { view->Toast.makeText(this, if(view is TextView) view.text else "Foo", Toast.LENGTH_SHORT).show() }

        Text("test").run {
            background {
                underline {
                    draw()
                }
            }
        }

    }

    abstract class Item

    class MediaItem : Item()

    inline fun <reified T> withCorrectType(toBeChecked: Item, block: (T) -> Unit) {
        if (toBeChecked !is T) {
            throw IllegalArgumentException("Invalid type")
        }
        block.invoke(toBeChecked)
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit)
            = observe(this@MainActivity, Observer { observe(it) })

    suspend  fun search() : Result{
        return SearchRepositoryProvider.provideSearchRepository2().searchUser2("Korea", "Java").await()
    }

    fun searchNotSuspend() : Result{
        return SearchRepositoryProvider.provideSearchRepository2().searchUser2("Korea", "Java").execute().body()!!
    }

    fun <T, R> test(o: T, body : () -> R) : R {
        o.toString()
        return body()
    }
    fun <T, R> test(receiver: T, body : T.() -> R) : R = receiver.body()

    fun countInstances(list: List<String>): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        for (key in list) {
            if (key !in map) {
                map[key] = 0
            }
            map[key] = map.getOrDefault(key, 0) + 1
        }
        return map
    }

    class Text(val text: String) {
        fun draw() = print(text)
    }

    fun Text.underline(decorated: Text.() -> Unit) {
        print("_")
        this.decorated()
        print("_")
    }

    fun Text.background(decorated: Text.() -> Unit) {
        print("\u001B[43m")
        this.decorated()
        print("\u001B[0m")
    }

    fun fail() : Nothing {
        throw RuntimeException("Something went wrong")
    }

    fun <T> List<T>.tail() = drop(1)
    fun <T> List<T>.head() = first()

    tailrec fun <T> filter(l: List<T>, res: List<T>, f: (T) -> Boolean): List<T> {
        if (l.isEmpty()) {
            return res
        } else {
            return filter(l.tail(), if (f(l.head())) { res + listOf(l.head())} else { res }, f)
        }
    }

    private fun beginSearch(srsearch: String) {
        disposable =
                wikiApiServe.hitCountCheck("query", "json", "search", srsearch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> testTextView.text = "${result.query.searchinfo.totalhits} result found" },
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    /**
     * parcelize Annotation은
     * build.gradle 에서
     * androidExtensions {
     *      experimental = true
     * }
     * 구문을 넣어야 하며
     * 안드로이드 4.4이하에서 앱설치가 안되는 문제점 있음
     * 참고 : https://proandroiddev.com/parcelable-in-kotlin-here-comes-parcelize-b998d5a5fcac
     */
    @Parcelize
    data class Person(val name:String, val adress:String = "") : Parcelable

    class KotlinPerson {

        var name: String? = null

        var age: Int? = null
            set(value) {
                if (value in 0..120){
                    field = value
                } else{
                    throw IllegalArgumentException()
                }
            }
    }

    class TreeNode(var children : List<TreeNode>)

    fun flatTree(tree: TreeNode): List<TreeNode>
            =  listOf(tree, *tree.children.flatMap(::flatTree).toTypedArray())

    data class GitUser(val name:String,val age : Int? = null)

    fun  asyncAwait(){
        object : AsyncTask<Unit, Unit, GitUser?>(){

            private var exception : IOException? = null

            override fun doInBackground(vararg params: Unit?): GitUser? {
                val user : GitUser = GitUser("test")
                return user
            }

            override fun onPostExecute(result: GitUser?) {
                if(result != null){
                    var test = "asyncTask: [${result.name}]"
                    testTextView.text = test

                }
            }
        }.execute()
    }

    private fun((Int, Int)->Int).andHello(){
        this(1,1)
        println("Hello, world!")
    }
    private fun greet(val1:Int, val2:Int) : Int{
        println("Hey there! ${val1 + val2}")
        return val1 + val2
    }
    private fun greetAndHello(){
        ::greet.andHello()
    }

    fun same(val1: Int) = val1
    fun twice(val1: Int) = val1 * 2
    fun trice(val1: Int) = val1 * 3

    fun composed(val1: Int) = (::trice..::twice..::same..::trice) (val1)

    operator fun <T, R, V> ((T) -> R).rangeTo(other: (R) -> V): ((T) -> V) {
        return {
            other(this(it))
        }
    }

    //  showing dialog
    fun showDialog() {
        if (notesDialog == null)
        ////////////////////////////////////////////////////////////////
        //  making Alert dialog - admire beauty of kotlin
        ////////////////////////////////////////////////////////////////
            notesDialog = showNotesAlertDialog {

                cancelable = false

                closeIconClickListener {
                }

                doneIconClickListener {
                }
            }
        //  and showing
        notesDialog?.show()
    }

}

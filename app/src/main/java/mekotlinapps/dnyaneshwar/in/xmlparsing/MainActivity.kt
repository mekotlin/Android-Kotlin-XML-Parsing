package mekotlinapps.dnyaneshwar.`in`.xmlparsing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class MainActivity : AppCompatActivity() {

    var hashMap: ArrayList<HashMap<String, String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hashMap = ArrayList();

        progressBar.visibility = View.VISIBLE
        parselement()
        progressBar.visibility = View.GONE
    }

    private fun parselement() {
        var inputStream: InputStream? = null
        var documentBulderFactory: DocumentBuilderFactory? = null
        var documentBuilder: DocumentBuilder? = null
        var doc: Document? = null
        var element: Element? = null
        var nodeList: NodeList? = null
        var node: Node? = null

        try {
            inputStream = assets.open("student.xml")
        } catch (e: IOException) {
            println(e.toString())
            progressBar.visibility = View.VISIBLE
        }finally {
            progressBar.visibility = View.VISIBLE
        }

        documentBulderFactory = DocumentBuilderFactory.newInstance()

        try {
            documentBuilder = documentBulderFactory.newDocumentBuilder()
        } catch (e: ParserConfigurationException) {
            println(e.toString())
        }finally {
            progressBar.visibility = View.VISIBLE
        }

        try {
            doc = documentBuilder!!.parse(inputStream)
        } catch (e: SAXException) {
            println(e.toString())
        } catch (e: IOException) {
            e.toString()
        }finally {
            progressBar.visibility = View.VISIBLE
        }

        element = doc!!.documentElement
        element.normalize()

        nodeList = doc.getElementsByTagName("listitem")

        for (i in 0 until nodeList.length) {

            node = nodeList.item(i)
            if (node.nodeType == Node.ELEMENT_NODE) {

                var element_: Element = node as Element

                var studentid = element_.getElementsByTagName("studentid").item(0).textContent
                var name = element_.getElementsByTagName("name").item(0).textContent
                var class_ = element_.getElementsByTagName("class").item(0).textContent
                var division = element_.getElementsByTagName("division").item(0).textContent
                var address = element_.getElementsByTagName("address").item(0).textContent

                insertIntoHashMap(studentid, name, class_, division, address)
            }
        }

        var listAdapter = SimpleAdapter(this, hashMap, R.layout.list_item, arrayOf<String>("Id", "Name", "Class", "Division", "Address"), intArrayOf(R.id.tvId, R.id.tvName, R.id.tvClass, R.id.tvDivision, R.id.tvAddress)) as ListAdapter
        listView.adapter = listAdapter

        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                toast(listAdapter.getItem(i).toString())
            }
        })
    }

    fun insertIntoHashMap(id: String, name: String, class_: String, division: String, address: String) {

        var hashMap_: HashMap<String, String> = HashMap()
        hashMap_!!.put("Id", id)
        hashMap_!!.put("Name", name)
        hashMap_!!.put("Class", class_)
        hashMap_!!.put("Division", division)
        hashMap_!!.put("Address", address)

        hashMap!!.add(hashMap_)
    }
}

package com.hemonugi.currency_converter

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

data class Currency(
    val name: String,
    val value: String,
    val nominal: Int,
    val charCode: String,
)

const val ns = ""

class CrbXmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Currency> {
        inputStream.use { inputStream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, null)
            parser.nextTag()
            return readValCurs(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readValCurs(parser: XmlPullParser): List<Currency> {
        val currencies = mutableListOf<Currency>()

        parser.require(XmlPullParser.START_TAG, ns, "ValCurs")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag.
            if (parser.name == "Valute") {
                val currency = readValute(parser)
                if (currency != null) {
                    currencies.add(currency)
                }
            } else {
                skip(parser)
            }
        }
        return currencies
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readValute(parser: XmlPullParser): Currency? {
        parser.require(XmlPullParser.START_TAG, ns, "Valute")
        var name: String? = null
        var value: String? = null
        var nominal: Int? = null
        var charCode: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "Name" -> name = readField(parser, "Name")
                "Value" -> value = readField(parser, "Value")
                "Nominal" -> nominal = readField(parser, "Nominal").toInt()
                "CharCode" -> charCode = readField(parser, "CharCode")
                else -> skip(parser)
            }
        }
        if (name != null && value != null && nominal != null && charCode != null) {
            return Currency(name, value, nominal, charCode);
        }

        return null;
    }

    // Processes title tags in the feed.
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readField(parser: XmlPullParser, name: String): String {
        parser.require(XmlPullParser.START_TAG, ns, name)
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, name)
        return result
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
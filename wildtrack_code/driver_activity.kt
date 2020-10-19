
    //models for list
    private lateinit var exception_tflite_Louis:SpeciesClassifier
    private lateinit var inception_tflite_Louis:SpeciesClassifier
    private lateinit var nasnet_tflite_louis:SpeciesClassifier
    private lateinit var model_list:ArrayList<SpeciesClassifier>

    /**
     * Upload prediction result to a URL
     *
     * @param p_url URL to send the report to
     * @param kv_pairs key/value pairs to encode in the query parameters
     */
    private fun sendPredictionResult(p_url: String, kv_pairs: HashMap<String, String>)
    {

        try {
            var url=p_url
            if(kv_pairs.isNotEmpty())
                {
                url+="?"
                var pairs= mutableListOf<String>()
                for((k,v) in kv_pairs)
                    {
                    val new_pair=k+"="+URLEncoder.encode(v,"UTF-8")
                    pairs.add(new_pair)
                    }
                var req_kvp=TextUtils.join("&",pairs)
                url+=req_kvp
                }
            readURLAsText(url)
            }
        catch (e: Exception)
        {
            e.printStackTrace()
        }
    }


    /**
     * Load a bitmap by getting it from a URL
     *
     * @param src URL of an image
     */
    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            val to_ret=BitmapFactory.decodeStream(input)
            return to_ret
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }


    /**
     * Get lines of text from a URL
     *
     * @param urls URL of text lines to retrieve
     */
    private fun readURLAsText(urls: String):  ArrayList<String>   {
            val url = URL(urls)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            val myisreader=InputStreamReader(con.inputStream)
            val mybufferedreader=BufferedReader(myisreader)
            var inputLine=mybufferedreader.readLine()
            val myLines=ArrayList<String>()
            while(inputLine!=null)
                {
                myLines.add(inputLine)
                inputLine=mybufferedreader.readLine()
                }
            myisreader.close()
            return myLines
        }

    /**
     * Thread to perform batch processing/classification
     */
    fun launchThread() {
        Thread(Runnable {
            val run_id=DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val url_base = "http://192.168.1.76:8000/"
            val img_fof_url = url_base + "img_list_louis_test.txt"
            val list_of_files = readURLAsText(img_fof_url)
            System.out.println("Downloaded a list of " + list_of_files.size + " files!")
            for (f in 0 until list_of_files.size) {
                val img_url = url_base + list_of_files[f]
                val encoded_img_path = list_of_files[f]
                val img_url_encoded = url_base + encoded_img_path
                val tempBitmap = getBitmapFromURL(img_url_encoded)
                if (tempBitmap != null) {
                    for (m in model_list) {
                        val beforeTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                        val temp_result = m.recognizeImage(tempBitmap)
                        val afterTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
                        val infoMap=HashMap<String,String>()
                        infoMap.put("run_id",run_id)
                        infoMap.put("file",list_of_files[f])
                        infoMap.put("file_id",""+f+"")
                        infoMap.put("result",temp_result)
                        infoMap.put("model_name",m.getName())
                        infoMap.put("start_time",beforeTime)
                        infoMap.put("end_time",afterTime)
                        val send_req_url="http://192.168.1.76:5000/report_image"
                        sendPredictionResult(send_req_url,infoMap)

                    }
                } else {
                    System.out.println("Bitmap from " + img_url_encoded + " was null!")
                }
            }
        }).start()
    }




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val batchMode=false
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.second_activity)
        exception_tflite_Louis=SpeciesClassifier(
            assets,
            "xception.tflite",
            "species_13_labels.txt",
            224,
            "exception_louis_direct"
        )
        inception_tflite_Louis=SpeciesClassifier(
            assets,
            "InceptionResNetV2.tflite",
            "species_13_labels.txt",
            299,
            "Inception_louis_direct"
        )
        nasnet_tflite_louis=SpeciesClassifier(
            assets,
            "NASNetLarge.tflite",
            "species_13_labels.txt",
            331,
            "nasnet_louis_direct"
        )
        model_list= ArrayList()
        myVotes= ArrayList()
        model_list.add(exception_tflite_Louis)
        model_list.add(inception_tflite_Louis)
        model_list.add(nasnet_tflite_louis)

        if(batchMode){
            //launchThread()
            }



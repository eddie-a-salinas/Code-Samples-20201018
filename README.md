# Code-Samples-20201018

Please find links to notes and code samples below.


## WildTrack AI (Fall 2020)


<img align="left" src="WTMobil_AmurTiger.Screenshot.png" width="25%">

*Keywords* : Android/Kotlin/Java, Android Development, Deep Learning, Computer Vision, Footprint identification technology (FIT), VGG, Tensorflow, XCeption/Inception/NASNet  
<br><br>
[WildTrack](https://wildtrack.org/) is a collaborative effort towards automation of animal footprints identification ([FIT](https://wildtrack.org/learn-more/footprint-identification-fit/)) with computer vision and AI/ML technology.  Among those efforts ia a project to make available an Android App for taking photos of footprints, performing classification on the device, and perhaps a way for users to curate images.

My contributions are on the Android app and testing the models on the device.  I have been using Android Studio and an emulated device.  Using TensorflowLite, the app runs 3 models to classify an image.  The three models are XCeption, Inception, and NasNet.  Currently, the app, being in development on a virtual device, can take input via the photo library, or the on-board camera.  A "batch" mode which downloads a file-of-files and runs inference on each of those has been in progress recently as well.

This repo includes a kotlin snippet [driver_activity](https://github.com/eddie-a-salinas/Code-Samples-20201018/blob/main/wildtrack_code/driver_activity.kt) of a driver class of that invokes the models for the batch mode.
It also includes a [kotlin class](https://github.com/eddie-a-salinas/Code-Samples-20201018/blob/main/wildtrack_code/SpeciesClassifier.kt) for implementing the classification function.

A Jupyter/Python notebook is also [here](https://github.com/eddie-a-salinas/Code-Samples-20201018/blob/main/wildtrack_code/Explore_results.ipynb) demonstrating strengths and weaknesses in the models.
<br><br><br><br>
## FireCloud (2015-2018)

*Keywords* : Javascript, Clojure/ClojureScript, React, HTML, CSS, visualization, object-oriented programming, AJAX, docker, WDL, somatic variant pipelines

[Firecloud](https://portal.firecloud.org/) is a web-based platform for organized batch job submission of containerized WDL workflows to Google Cloud.

Learn more about firecloud [here](https://datascience.cancer.gov/news-events/events/firecloud-future-cancer-genome-analysis) or from this [biorxiv link/paper](https://www.biorxiv.org/content/10.1101/209494v1).

I contributed not only workflows ([WDLs](https://github.com/openwdl/wdl)) for firecloud but also to the "orchestration" and UI layers.

Below are two links to pull-requests I participated in 

[Method Configurations](https://github.com/broadinstitute/firecloud-ui/pull/37)


[Method Repository/Configurations and Permissions](https://github.com/broadinstitute/firecloud-ui/pull/199)


The [firecloud-ui git repo](https://github.com/broadinstitute/firecloud-ui) has additional commits some including contributions from myself.


## VDJ Server (2013-2015)

*Keywords* : Javascript, D3, HTML, CSS, visualization, python, object-oriented programming

VDJ Server is a web interface for immune repertoire NGS data analysis.  I contributed to the charting (D3, HTML, Javascript) as well as the repertoire summarization (python-based igblast parsing and data).  

In the RepSum repo you can see some python codes of mine.  The python code has been modified by others, so
I have "cherry picked" a few commits showing some use of object-oriented python.

1.Code starting a class for parsing IMGT data

[https://bitbucket.org/vdjserver/repertoire-summarization/commits/98bf2779ba43fd9adbb41c322c036e4d061e411f](https://bitbucket.org/vdjserver/repertoire-summarization/commits/98bf2779ba43fd9adbb41c322c036e4d061e411f)

2. Utility for a counting dict/map

[https://bitbucket.org/vdjserver/repertoire-summarization/commits/458456c1f7162d3baf52996b0f14fae38164fc17](https://bitbucket.org/vdjserver/repertoire-summarization/commits/458456c1f7162d3baf52996b0f14fae38164fc17 )

3. A class for manipulating sequence alignments

[https://bitbucket.org/vdjserver/repertoire-summarization/commits/46b7d09fbd30d50000b0424be7b405524584b455](https://bitbucket.org/vdjserver/repertoire-summarization/commits/46b7d09fbd30d50000b0424be7b405524584b455)

4. A class implementing a thread interface for parallel annotation of sequences/alignments

[https://bitbucket.org/vdjserver/repertoire-summarization/commits/2603cbd32b543ac92726301f9ecfb134aa894612](https://bitbucket.org/vdjserver/repertoire-summarization/commits/2603cbd32b543ac92726301f9ecfb134aa894612)


The general repo (commit non-specific) is linked to here : 
https://bitbucket.org/vdjserver/repertoire-summarization/src/master/

A demo/link of charting code is [here](http://eddie-salinas-com.s3-website-us-east-1.amazonaws.com/vdj_server/charts/dynaChart.html) .

The code isn't used "as is" but was adapted at the time of deployment in 2014/2015.  Something like it seems to still be in use currently from what I can tell from these [release notes](https://vdjserver.org/docs/QuickStart/VDJServer_Release1.1_Basic_Analysis_Workflow.pdf) (see supplementary Figures 6,7)



## COMET (2013-Present)

*Keywords* : Java, MCMC (Markov-Chain Monte Carlo), MCMCMC(Metropolis-Coupled Markov-Chain Monte Carlo), Thermodynamic Integration, Bayes Factors, applied coalescent theory, trees & multiple mergers genealogies, phylogenetics

COMET stands for COalescent with Multiple mergers Employing Thermodynamic integration.  It is an *unpaid* project.  COMET is a program for MCMC-based sampling of multiple mergers genealogies based on multiple mergers coalescent models. It can be used with different BETA settings for running chains at various heats so that marginal probabilities of models can be computed using thermodynamic integration.

Learn more here : [http://eddie-salinas-com.s3-website-us-east-1.amazonaws.com/COMET/index.html](http://eddie-salinas-com.s3-website-us-east-1.amazonaws.com/COMET/index.html)

The bitbucket git repo is [here](https://bitbucket.org/eddieasalinas/comet/src/master/). 

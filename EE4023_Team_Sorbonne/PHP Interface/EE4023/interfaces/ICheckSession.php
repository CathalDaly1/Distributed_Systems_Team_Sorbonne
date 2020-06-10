<?php
    //This starts the users session and holds username and game data
    session_start();
    //WSDL URL to soap client
    $wsdl = "http://212.17.39.218:8080/TTTWebApplication/TTTWebService?WSDL";
    //Checks if client has already been set, if not then it set's it using the WSDL URL
    if (!isset($client))
    {
        try
        {
            //This Client will be uses throughout the code and essentially holds a connection to the webservice
            $client = new SoapClient($wsdl, array('trace' => true, 'exceptions' => true));
        }
        catch (Exception $ex)
        {}
    }



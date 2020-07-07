<?php
    include_once "Lib/simple_html_dom.php";
    $time = [];
    $longitude = [];
    $latitude = [];
    if(!empty($_GET['aircraft_name'])){
        $name = $_GET['aircraft_name'];
        $all_aircrafts = "https://data-live.flightradar24.com/zones/fcgi/feed.js?bounds=59.19,40.08,24.56,57.93&faa=1&satellite=1&mlat=1&flarm=1&adsb=1&gnd=1&air=1&vehicles=1&estimated=1&maxage=14400&gliders=1&stats=1&selected=24851081&ems=1";
        $ch = curl_init($all_aircrafts);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_HEADER, false);
        $html = curl_exec($ch);
        $data = json_decode($html);
        $arr_coordinates = [];
        $id_aircaft = "";
        foreach($data as $key =>$value){
            if(is_array($value)){
                foreach($value as $inner_value){
                    if(strcmp($name,$inner_value)==0){
                        array_push($arr_coordinates, $value[1], $value[2]);
                        $id_aircaft = $key;
                        break;
                    }
                   
                }

            }
        }
        if(!empty($id_aircaft)){
            $ch = curl_init("https://data-live.flightradar24.com/clickhandler/?version=1.5&flight=" . $id_aircaft);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_HEADER, false);
            $new_html = curl_exec($ch);
            $new_data = json_decode($new_html,true);
            foreach($new_data as $new_key=>$new_value){
                if(strcmp($new_key,"trail")==0){
                    foreach($new_value as $key=>$value){
                        array_push($latitude, $value['lat']);
                        array_push($longitude, $value['lng']);
                        array_push($time, $value['ts']);
                    }
                }
            }
            if(!empty($_GET['date'])){
                for($i = 0; $i < count($time); $i++){
                    if(strtotime($_GET['time'])==$time[$i]){
                        echo "В это время координаты самолёта были".$latitude[$i]." и ".$longitude[$i];
                        
                    }
                }
            }
        }
        else {
            echo "Такого рейса не найдено";
        }
    }
    else{
        echo "Пожалуйста введите номер рейса";
    }
?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Быстрый старт. Размещение интерактивной карты на странице</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style>
        *{
            margin: 0px;
            padding: 0px;
        }
        #map{
            width: 800px;
            height: 400px;
            margin: auto;
        }
        .form_block{
            margin: auto;
            width: 400px;
        }
        input{
            display: block;
            margin: auto;
        }
        .form_block #btn_form{
            display: block;
            width: 60%;
            margin: 10px auto;
        }
        
    </style>
    <script src="https://api-maps.yandex.ru/2.1/?apikey=6b88b84b-7d16-46fb-8533-9872c2bb475b&lang=ru_RU" type="text/javascript">
    </script>
    <script type="text/javascript">
    ymaps.ready(function () {
    var myMap = new ymaps.Map('map', {
        center: [56.310073,43.998007],
                    zoom: 3,
        controls: []
    });
    let latitude = "<?php echo json_encode($latitude)?>";
    let longitude = "<?php echo json_encode($longitude)?>";
    let time_aircraft = "<?php echo json_encode($time)?>";
    latitude = JSON.parse(latitude);
    longitude = JSON.parse(longitude);
    time_aircraft = JSON.parse(time_aircraft);
    console.log(latitude);
    console.log(longitude);
    var geometry = [];
    for(let i = latitude.length-1 ;i >= 0; i--){
        geometry.push([latitude[i],longitude[i]]);
    }
		var properties = {
				hintContent: "Ломаная линия"
			},
			options = {
				draggable: true,
				strokeColor: '#ff0000',
				strokeWidth: 5
 
			},
			polyline = new ymaps.Polyline(geometry, properties, options);
 
			myMap.geoObjects.add(polyline);	
});
    </script>
</head>

<body>
    <div id="map"></div>
    <section class="form_block">
        <form action="" method="GET">
            <label for="name">Введите номер рейса</label>
            <input type="text" name="aircraft_name" id="name">
            <label for="date">Введите время</label>
            <input type="text" name="date" id="date">
            <button type="submit" id="btn_form">Поиск</button>
        </form>
    </section>
</body>

</html>}

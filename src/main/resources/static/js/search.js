     fetch('https://apis.datos.gob.ar/georef/api/provincias')
    .then(response => response.json())
    .then(respuestaApi => {
        let provincias = respuestaApi.provincias;

        const $select = document.getElementById("province")
        //empty option
        const $option = document.createElement("option")
        $option.value = ""
        $option.innerHTML = "Seleccione una provincia"
        $select.appendChild($option)
        provincias.forEach(provincia => {
            const $option = document.createElement("option")
            $option.value = provincia.nombre
            $option.innerHTML = provincia.nombre
            $select.appendChild($option)
        })




    })
    .catch((err) => {
        console.log(err)
    })

    var selectProvince = document.getElementById('province')
    var selectCity = document.getElementById('city')
    
    selectProvince.addEventListener('change', function(){
        console.log(selectProvince.value)
        fetch('https://apis.datos.gob.ar/georef/api/municipios?provincia='+  selectProvince.value + '&max=100')
    .then(response => response.json())
    .then(respuestaApi => {
        
       
        let ciudades = respuestaApi.municipios;
        // selectProvince.removeChild()
        const $select = document.getElementById("city")
        //empty option
        while (selectCity.firstChild) {
            selectCity.removeChild(selectCity.firstChild);
        }
        const $option = document.createElement("option")
        $option.value = ""
        $option.innerHTML = "Seleccione una ciudad"
        $select.appendChild($option)
        ciudades.forEach(ciudad => {
            const $option = document.createElement("option")
            $option.value = ciudad.nombre
            $option.innerHTML = ciudad.nombre
            $select.appendChild($option)
        })

        })  
    })
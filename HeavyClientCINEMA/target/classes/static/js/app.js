var app = (function (){

    var _cinema;
    var _date;
    var _functions;

    function _setCinemaName(cinema) {
        _cinema = cinema;
    };

    function _setDate(date) {
        _date = date;
    };

    function _updateFunctionsData(functions){
        _functions = [];
        functions.map(function (f) {
           _functions.push({movieName: f.movie.name, genre: f.movie.genre, hour: f.date.substring(11, 16)}); 
        });
        _updateTableData();
    }

    function _updateTableData(){
        $("#functionsTable > tbody").empty()
        _functions.map(function (movie) {
            var onClick = "app.openSeats(\""+movie.hour+"\")";
            var btn = "<button class='btn btn-outline-primary' value='Open Seats' onclick=" + onClick + ">Open seats</input>";
            var fila = '<tr><td>' + movie.movieName + '</td><td>' + movie.genre + '</td><td>' + movie.hour + '</td><td>' + btn + '</tr>';
            $("#functionsTable > tbody").append(fila)
        })
        _showMovies();
    }

    function _showMovies(){
        $("#cinemaSelected").text("Cinema Selected: "+_cinema);
        $("#movies").text("Movies:");
        document.getElementById('functions').style.visibility = "visible";
    }

    function _updateSeats(functions){
        _clearCanvasSeats();
        $("#availabilityOf").text("Availability of: "+ functions[0].movie.name);
        var seats = functions[0].seats;
        var c = document.getElementById("seatsCanvas");
        var ctx = c.getContext("2d");
        ctx.fillStyle = "#000000";
        ctx.fillRect(c.width*0.2, c.height*0.05, c.width*0.6, c.height*0.075);
        var x = c.width * 0.1; var y = c.height * 0.20;
        var w = (c.width) * 0.8; var h = (c.height) * 0.75;
        var l;
        if (w < h){
            l = w*0.8/seats[0].length;
        }else{
            l = h*0.8/seats.length;
        }
        var dx = (w - (l*seats[0].length)) / seats[0].length;
        var dy = (h - (l*seats.length)) /seats.length;

        seats.map(function (row){
            x = c.width * 0.1;
            row.map(function (seat){
                ctx.fillStyle = "#333842";
                if (!seat){
                    ctx.fillStyle = "#B40431";
                }
                ctx.fillRect(x, y, l, l);
                x = x + l + dx;
            })
            y = y + l + dy;
        })
    }

    function _clearCanvasSeats(){
        var canvas = document.getElementById("seatsCanvas");
        var ctx = canvas.getContext("2d");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.beginPath();
    }
    

    return{
        updateFunctions: function (cinema, date){
            _setCinemaName(cinema);
            _setDate(date);
            apimock.getFunctionsByCinemaAndDate(cinema, date, _updateFunctionsData);
        },

        openSeats(hour){
            apimock.getFunctionsByCinemaAndDate(_cinema,_date+" "+hour, _updateSeats);
        }
    }

})();
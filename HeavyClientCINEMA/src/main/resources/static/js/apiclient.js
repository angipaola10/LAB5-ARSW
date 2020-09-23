api = (function () {

    return {
         getFunctionsByCinema: function(cinema_name, callback) {
            //http://localhost:8080/cinemas/{cinemaName}
            $.getJSON("http://localhost:8080/cinemas/" + cinema_name, function (data) {
                callback(data);
            });
        },
    
        getFunctionsByCinemaAndDate: function(cinema_name, fdate, callback) {
            //http://localhost:8080/cinemas/{cinemaName}/{date}
            $.getJSON("http://localhost:8080/cinemas/" + cinema_name +"/"+ fdate, function (data) {
                callback(data);
            });
        },
    
        getFunctionByCinemaMovieAndDate: function (cinema_name, fdate, movie_name, callback){
            //http://localhost:8080/cinemas//{cinemaName}/{date}/{movieName}
            $.getJSON("http://localhost:8080/cinemas/"+cinema_name+"/"+ fdate+"/"+movie_name, function (data) {
                callback(data);
            });
        }
    }

})();
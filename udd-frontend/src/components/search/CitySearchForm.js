import React, { useEffect, useState } from "react";
import { applicationService } from "../../services/ApplicationService";
import Axios from "axios";

const CitySearchForm = () => {

	const [radius, setRadius] = useState(0.0);
    const [cities, setCities] = useState([]);
    const [selectedCity, setSelectedCity] = useState("SelectCity");
    const [searchResults, setSearchResults] =useState([])

    useEffect(() => {
        const fetchCities = async () => {
            Axios.get(`/application/cities`)
            .then((res) => {
                setCities(res.data)
            })
            .catch((err) => {
                console.log(err);
            });
        };
    
        fetchCities();
      }, []);

	const handleSubmit = (e) => {
		e.preventDefault();

        if(radius<=0 || selectedCity=="SelectCity"){
            alert('Radius mora biti veci od 0 i grad mora biti selektovan')
        }
        else{
            const request = 
        {
            "CityId": selectedCity,
            "Radius": radius
        }

        console.log(request)
        

        Axios.post('search/geolocation', request)
            .then(response => {
                if(response.status==200){
                    console.log(response.data)
                    setSearchResults(response.data)
                }
            })
            .catch(error => {
                console.log(error);
        });
        }

        
	};

    const handleClear = () =>{
        setRadius(0.0)
        setSearchResults([])
        setSelectedCity("SelectCity")
    }


	return (
		<React.Fragment>
			<form className="forms-sample" method="post" onSubmit={handleSubmit}>
            <div class="container">
                <div class="row mb-2">
                    <div class="col-sm">
                        City
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select" id="select-restaurant" value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
							<option value={"SelectCity"} key={"SelectCity"}>Select city</option>
                            {cities.map((city) => {
								return (
									<option value={city.id} key={city.id}>
										{city.name}
									</option>
								);
							})}
						</select>
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="firstName" placeholder="Radius" value={radius} onChange={(e) => setRadius(e.target.value)} />
                    </div>
                </div>
                <button type="submit" className="btn btn-primary mr-2 float-right">
					Search
			    </button>
                <button type="reset" onClick={() => handleClear()} className="btn btn-primary mr-2 float-right">
					Clear
			    </button>
            </div>
			</form>
            <div className="mt-5">
                <h2>Search Results</h2> 

                {searchResults.map((searchResult) => (
                    <div className="row p-0 m-auto my-4">
                        <div className="col-12 d-flex justify-content-between">
                            <div>
                                <span>
                                    {searchResult.FirstName} {searchResult.LastName}
                                </span>
                                <span className="mx-5">{searchResult.Qualifications} stepen strucne spreme</span>
                            </div>
                            <span className="text-secondary">
                                <i className="fa fa-map-marker  mx-2"></i>
                                {searchResult.City}
                            </span>
                        </div>
                        <div className="col-12  shadow p-2 mt-2">
                            <span dangerouslySetInnerHTML={{ __html: searchResult.Highlight }} />
                        </div>

                    </div>
                ))}
                
            </div>
		</React.Fragment>
	);
};

export default CitySearchForm;

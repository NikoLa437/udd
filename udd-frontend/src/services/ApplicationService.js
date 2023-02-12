import Axios from "axios";

export const applicationService = {
	findAllCities,

};


function findAllCities() {

	 Axios.get(`/application/qualifications`)
		.then((res) => {
			return res.data
		})
		.catch((err) => {
			console.log(err);
		});

}
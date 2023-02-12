import React, { useEffect, useState } from "react";
import { applicationService } from "../../services/ApplicationService";
import Axios from "axios";

const CreateApplicationForm = () => {

	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
	const [address, setAddress] = useState("");
	const [phoneNumber, setPhoneNumber] = useState("");
	const [cv, setCV] = useState("");
	const [coverLetter, setCoverLetter] = useState("");
	const [selectedQualifications, setSelectedQualifications] = useState("");
	const [qualifications, setQualifications] = useState(["I", "II"]);
	const [selectedCity, setSelectedCity] = useState("");
	const [cities, setCities] = useState([]);

    const clearFields = () => {
        setFirstName("");
        setLastName("");
        setUsername("");
        setEmail("");
        setPassword("");
        setPhoneNumber("");
        setCV("");
        setCoverLetter("");
        setSelectedQualifications("");
        setSelectedCity("");
        setAddress("")
    }

	const handleSubmit = (e) => {
		e.preventDefault();
        let formData = new FormData();

        const config = {     
            headers: { 'content-type': 'multipart/form-data' }
        }        

        formData.append("FirstName", firstName)
        formData.append("LastName", lastName)
        formData.append("Username", username)
        formData.append("Email", email)
        formData.append("Password", password)
        formData.append("Address", address)
        formData.append("PhoneNumber", phoneNumber)
        formData.append("CV", cv)
        formData.append("CL", coverLetter)
        formData.append("Qualifications", selectedQualifications)
        formData.append("CityId", selectedCity)

        Axios.post('application', formData, config)
            .then(response => {
                if(response.status==201){
                    alert("Uspesno ste aplicirali za posao")
                    clearFields();
                }
            })
            .catch(error => {
                console.log(error);
        });
	};

    useEffect(() => {
        const fetchData = async () => {
            Axios.get(`/application/qualifications`)
            .then((res) => {
                setQualifications(res.data)
            })
            .catch((err) => {
                console.log(err);
            });
        };

        const fetchCities = async () => {
            Axios.get(`/application/cities`)
            .then((res) => {
                setCities(res.data)
            })
            .catch((err) => {
                console.log(err);
            });
        };
    
        fetchData();
        fetchCities();
      }, []);


	return (
		<React.Fragment>
			<form className="forms-sample" method="post" onSubmit={handleSubmit}>
				<div className="form-group">
					<label for="firstName">User name</label>
					<input type="text" required className="form-control" id="firstName" placeholder="User name" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
				</div>
				<div className="form-group">
					<label for="lastName">Last name</label>
					<input type="text" required className="form-control" id="lastName" placeholder="Last name" value={lastName} onChange={(e) => setLastName(e.target.value)} />
				</div>
                <div className="form-group">
					<label for="email">Username</label>
					<input type="text" required className="form-control" id="username" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
				</div>
                <div className="form-group">
					<label for="email">Email</label>
					<input type="text" required className="form-control" id="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
				</div>
                <div className="form-group">
					<label for="password">Password</label>
					<input type="password" required className="form-control" id="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
				</div>
				<div className="form-group">
					<label for="address">Address</label>
					<input type="text" required className="form-control" id="address" placeholder="Address" value={address} onChange={(e) => setAddress(e.target.value)} />
				</div>
                <div className="form-group">
					<label for="phoneNumber">Phone number</label>
					<input type="text" required className="form-control" id="phoneNumber" placeholder="Phone number" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
				</div>
                <div className="form-group">
					<label for="phoneNumber">Qualification</label>
					<select required className="custom-select my-1 mr-sm-2 row" id="select-restaurant" value={selectedQualifications} onChange={(e) => setSelectedQualifications(e.target.value)}>
										{qualifications.map((qualification) => {
											return (
												<option value={qualification} key={qualification}>
													{qualification}
												</option>
											);
										})}
									</select>
				</div>
                <div className="form-group">
					<label for="phoneNumber">Cities</label>
					<select required className="custom-select my-1 mr-sm-2 row" id="select-restaurant" value={selectedCity} onChange={(e) => setSelectedCity(e.target.value)}>
										{cities.map((city) => {
											return (
												<option value={city.id} key={city.id}>
													{city.name}
												</option>
											);
										})}
									</select>
				</div>
                <div className="form-group">
					<label for="cv">CV</label>
					<input  type="file" required className="form-control" id="cv" placeholder="CV" onChange={(e) => setCV(e.target.files[0])} />
				</div>
                <div className="form-group">
					<label for="coverLetter">Cover Letter</label>
					<input  type="file" required className="form-control" id="coverLetter" placeholder="Cover letter" onChange={(e) => setCoverLetter(e.target.files[0])} />
				</div>
				<button type="submit" className="btn btn-primary mr-2">
					Submit
				</button>
			</form>
		</React.Fragment>
	);
};

export default CreateApplicationForm;

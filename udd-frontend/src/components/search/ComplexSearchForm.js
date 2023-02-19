import React, { useEffect, useState } from "react";
import { applicationService } from "../../services/ApplicationService";
import Axios from "axios";

const ComplexSearchForm = () => {

	const [firstName, setFirstName] = useState("");
    const [firstNameOperator, setFirstNameOperator] = useState("AND")
	const [lastName, setLastName] = useState("");
    const [lastNameOperator, setLastNameOperator] = useState("AND")
    const [qualifications, setQualifications] = useState("");
    const [qualificationOperator, setQualificationOperator] = useState("AND")
	const [cv, setCV] = useState("");
    const [cvOperator, setCvOperator] = useState("AND")
	const [coverLetter, setCoverLetter] = useState("");
    const [coverLetterOperator, setCoverLetterOperator] = useState("AND")
    const [searchResults, setSearchResults] =useState([])

	const handleSubmit = (e) => {
		e.preventDefault();

        const request = [
            {
                "Field": "firstName",
                "Value": firstName == "" ? null: firstName,
                "Operator": firstNameOperator
            },
            {
                "Field": "lastName",
                "Value": lastName == "" ? null: lastName,
                "Operator": lastNameOperator
            },
            {
                "Field": "qualifications",
                "Value": qualifications == "" ? null: qualifications,
                "Operator": qualificationOperator
            },
            {
                "Field": "cvContent",
                "Value":  cv == ""? null: cv,
                "Operator": cvOperator
            }
            ,
            {
                "Field": "coverLetterContent",
                "Value": coverLetter == "" ? null: coverLetter,
                "Operator": coverLetterOperator
            }
        ]

        Axios.post('search/complex', request)
            .then(response => {
                if(response.status==200){
                    console.log(response.data)
                    setSearchResults(response.data)
                }
            })
            .catch(error => {
                console.log(error);
        });
	};

    const handleClear = () =>{
        setFirstName("")
        setFirstNameOperator("AND")
        setLastName("")
        setLastNameOperator("AND")
        setCV("")
        setCvOperator("AND")
        setQualificationOperator("AND")
        setQualifications("")
        setCoverLetter("")
        setCoverLetterOperator("AND")
        setSearchResults([])
    }


	return (
		<React.Fragment>
			<form className="forms-sample" method="post" onSubmit={handleSubmit}>
            <div class="container">
                <div class="row mb-2">
                    <div class="col-sm">
                        FirstName
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="firstName" placeholder="User name" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select" id="select-restaurant" value={firstNameOperator} onChange={(e) => setFirstNameOperator(e.target.value)}>
                            <option value="AND" key="AND">
                                AND
                            </option>
                            <option value="OR" key="OR">
                                OR
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-sm">
                        LastName
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="lastName" placeholder="Last name" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select" id="select-restaurant" value={lastNameOperator} onChange={(e) => setLastNameOperator(e.target.value)}>
                            <option value="AND" key="AND">
                                AND
                            </option>
                            <option value="OR" key="OR">
                                OR
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-sm">
                        Qualifications
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="lastName" placeholder="Qualifications" value={qualifications} onChange={(e) => setQualifications(e.target.value)} />
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select" id="select-restaurant" value={qualificationOperator} onChange={(e) => setQualificationOperator(e.target.value)}>
                            <option value="AND" key="AND">
                                AND
                            </option>
                            <option value="OR" key="OR">
                                OR
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-sm">
                        CV Content
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="lastName" placeholder="Cv content" value={cv} onChange={(e) => setCV(e.target.value)} />
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select" id="select-restaurant" value={cvOperator} onChange={(e) => setCvOperator(e.target.value)}>
                            <option value="AND" key="AND">
                                AND
                            </option>
                            <option value="OR" key="OR">
                                OR
                            </option>
                        </select>
                    </div>
                </div>
                <div class="row mb-2    ">
                    <div class="col-sm">
                        Cover Letter Content
                    </div>
                    <div class="col-sm">
                        <input type="text" className="form-control" id="lastName" placeholder="Cover letter content" value={coverLetter} onChange={(e) => setCoverLetter(e.target.value)} />
                    </div>
                    <div class="col-sm">
                        <select required className="custom-select " id="select-restaurant" value={coverLetterOperator} onChange={(e) => setCoverLetterOperator(e.target.value)}>
                            <option value="AND" key="AND">
                                AND
                            </option>
                            <option value="OR" key="OR">
                                OR
                            </option>
                        </select>
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

export default ComplexSearchForm;

import React from "react";
import CreateApplicationForm from "../components/application/CreateApplicationForm";
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

const HomePage = () => {
	return (
		<React.Fragment>
			<Tabs
				defaultActiveKey="home"
				id="uncontrolled-tab-example"
				className="mb-3"
				>
				<Tab eventKey="home" title="Apple for job">
					<CreateApplicationForm />
				</Tab>
				<Tab eventKey="search" title="Search">
					<div>
						todo
					</div>
				</Tab>
				<Tab eventKey="search_location" title="Search by location">
					<div>
						todo
					</div>
				</Tab>
			</Tabs>
		</React.Fragment>
	);
};

export default HomePage;
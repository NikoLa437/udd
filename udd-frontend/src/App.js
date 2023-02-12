import './App.css';
import { HashRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import HomePage from './pages/HomePage';

function App() {
  return (
    <Router>
			      <Switch>
                <Route path="/" component={HomePage} />
			      </Switch>
		</Router>
  );
}

export default App;

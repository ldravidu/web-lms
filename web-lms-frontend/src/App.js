import "./App.css";
import { Router, Routes, Route } from "react-router-dom";
import ContentList from "./components/ContentList";
import ContentDetails from "./components/ContentDetails";
import UploadComponent from "./components/UploadComponent";
import Navbar from "./components/Navbar";

const App = () => {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<ContentList />}></Route>
          <Route path="/upload" element={<UploadComponent />}></Route>
          <Route path="/content/:id" element={<ContentDetails />}></Route>
        </Routes>
      </div>
    </Router>
  );
};

export default App;

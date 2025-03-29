import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navigation from "./components/Navigation";
import UploadContent from "./components/UploadContent";
import CourseContentList from "./components/CourseContentList";

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen">
        <Navigation />
        <main className="container mx-auto p-4">
          <Routes>
            <Route path="/" element={<UploadContent />} />
            <Route path="/list" element={<CourseContentList />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;

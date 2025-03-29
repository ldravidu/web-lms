import React from "react";
import { Link } from "react-router-dom";

const Navigation = () => {
  return (
    <nav className="bg-primary text-white p-4 shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">Course Content Manager</h1>
        <div className="space-x-4">
          <Link to="/" className="hover:text-secondary">
            Upload Content
          </Link>
          <Link to="/list" className="hover:text-secondary">
            Content List
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navigation;

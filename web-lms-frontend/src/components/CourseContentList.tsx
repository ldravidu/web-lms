// src/components/CourseContentList.tsx
import { useState, useEffect } from "react";
import axios from "axios";

const CourseContentList = () => {
  const [contents, setContents] = useState<Array<any>>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchContents = async () => {
      try {
        console.log("Fetching contents...");
        const response = await axios.get("/api/content/");
        console.log("Contents:", response.data);
        setContents(response.data);
        setError("");
      } catch (err: any) {
        console.error("Failed to fetch contents:", err);
        setError(err.response?.data || "Failed to fetch contents");
      } finally {
        setLoading(false);
      }
    };

    fetchContents();
  }, []);

  if (loading) {
    return (
      <div className="p-4 text-center">
        <p className="text-muted-foreground">Loading contents...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="p-4 text-center">
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <h2 className="text-2xl font-bold">Course Contents</h2>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
        {contents.map((content: any) => (
          <div
            key={content.id}
            className="bg-white p-4 rounded-lg shadow-sm hover:shadow-md transition-shadow"
          >
            <h3 className="font-semibold mb-2">{content.fileName}</h3>
            <p className="text-sm text-muted-foreground mb-1">
              Course: {content.courseId}
            </p>
            <p className="text-sm text-muted-foreground mb-1">
              Type: {content.fileType}
            </p>
            <p className="text-sm text-muted-foreground">
              Uploaded: {new Date(content.uploadDate).toLocaleString()}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseContentList;

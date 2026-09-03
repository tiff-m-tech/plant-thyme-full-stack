import { useState } from "react";
import PageTitle from "../ui/PageTitle";
import ProgressPictureCard from "../cards/ProgressPictureCard";

export default function ProgressGallery({ plant }) {
    const pictures = plant.progressPictures;
    const [selectedImage, setSelectedImage] = useState("");
    const today = new Date().toLocaleDateString("en-US");

    function formatDate(date) {
        return new Date(date + "T00:00:00").toLocaleDateString("en-US", {
            month: "numeric",
            day: "numeric",
            year: "numeric",
        });
    }

    return (
        <section id="progressGallery">
            <PageTitle title="Progress Pictures" />
            <div>
                <label htmlFor="progress-upload" className="file-upload-label">
                    Add Progress Picture
                </label>
                <input
                    id="progress-upload"
                    type="file"
                    accept="image/*"
                    className="file-upload-input"
                    onChange={(event) => {
                        const file = event.target.files?.[0];
                        // Creates a temporary blob URL (ex blob:http://localhost...) that points to the selected image data so it can be previewed immediately.
                        setSelectedImage(file ? URL.createObjectURL(file) : undefined);
                    }}
                />
            </div>
            <div className="progress-pictures-cards-container">
                {pictures.map((progressPic) => (
                    <ProgressPictureCard
                        key={progressPic.id}
                        fileName={progressPic.picture}
                        date={formatDate(progressPic.date)}
                        src={`${import.meta.env.BASE_URL}images/progressPictures/${progressPic.picture}`}
                    />
                ))}
                {selectedImage && <ProgressPictureCard src={selectedImage} date={today} />}
            </div>
        </section>
    );
}

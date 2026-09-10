export default function ProgressPictureCard({ src, date }) {
    return (
        <div className="progress-picture-card">
            <div className="progress-picture-date">{date}</div>
            <img
                src={src}
                alt={`Progress picture of plant from ${date}`}
                className="progress-picture"
            />
        </div>
    );
}
